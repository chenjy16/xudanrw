package com.trade.rws.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trade.rws.common.DataSourceFetcher;
import com.trade.rws.dbselect.AbstractDBSelector;
import com.trade.rws.dbselect.DBSelector;
import com.trade.rws.dbselect.EquityDbManager;
import com.trade.rws.dbselect.OneDBSelector;
import com.trade.rws.dbselect.PriorityDbGroupSelector;
import com.trade.rws.enums.DBType;
import com.trade.rws.exception.ConfigException;
import com.trade.rws.group.DataSourceWrapper;
import com.trade.rws.group.TGroupDataSource;
import com.trade.rws.util.SpringDataSourceFetcher;
/**
 * 封装成dataSourceWrapper  然后 
 * 一个ConfigManager对应一个TGroupDataSource，
 * 主要用于将根据Group的dataID取得的对应配置字符串信（比如db0:rwp1q1i0, db1:rwp0q0i1），
 * 转化为真正的Group层的配置体系结构：一个Group层挂着两个Atom db0 与 db1 ， 则我们使用一个 Map<String,
 * DataSourceWrapper> 来表示 其中的String 为每个Atom DS 的dbKey ，DataSourceWrapper
 * 为经过封装的TAtomDataSource
 * ---这里需要解释一下，为什么不直接使用AtomDataSource？因为每个AtomDataSource还有相应的权重和优先级信息 因此，需要***方法
 * 其中，配置的每一个Atom DataSource也只是用Atom
 * 的dbKey表示，因此，我们还需要根据此dbKey取得Atom的配置信息，并且将它封装成一个AtomDataSource对象。 因此需要***方法
 *
 * 有了这个map能根据dbKey迅速的找到对应的Datasource也是不够的，我们的Group层应该是对应用透明的，
 * 因此，当我们的读写请求进来时，Group层应该能够根据配置的权重和优先级，自动的选择一个合适的DB上进行读写，
 * 所以，我们还需要将配置信息生成一个DBSelector来自动的完成根据权重、优先级选择合适的目标库 因此，需要***方法
 *
 */
public class ConfigManager {
	private static final Log logger = LogFactory.getLog(ConfigManager.class);
	private final TGroupDataSource tGroupDataSource;
	private boolean createTAtomDataSource = true;
	private Map<String/* Atom dbIndex */, DataSourceWrapper/* Wrapper过的Atom DS */> dataSourceWrapperMap = new HashMap<String, DataSourceWrapper>();
	private String  dsWeightCommaStr="";
	public ConfigManager(TGroupDataSource tGroupDataSource) {
		this.tGroupDataSource = tGroupDataSource;
	}

	/**
	 *构造有优先级信息的读写DBSelector 
	 */
	public void init() {
		List<DataSourceWrapper> dswList = parse2DataSourceWrapperList(dsWeightCommaStr);
		resetByDataSourceWrapper(dswList);
	}

	
	/**
	 * 根据普通的DataSource构造读写DBSelector
	 */
	public void init(List<DataSourceWrapper> dataSourceWrappers) {
		if ((dataSourceWrappers == null) || dataSourceWrappers.size() < 1) {
			throw new ConfigException("dataSourceWrappers不能为null且长度要大于0");
		}
		createTAtomDataSource = false;
		resetByDataSourceWrapper(dataSourceWrappers);
	}
	
	
	// configInfo样例: db1:rw, db2:r, db3:r
	private void parse(String dsWeightCommaStr) {
		List<DataSourceWrapper> dswList = parse2DataSourceWrapperList(dsWeightCommaStr);
		resetByDataSourceWrapper(dswList);
	}


	/**
	 * 警告: 逗号的位置很重要，要是有连续的两个逗号也不要人为的省略掉， 数据库的个数 =
	 * 逗号的个数+1，用0、1、2...编号，比如"db1,,db3"，实际上有3个数据库，
	 * 业务层通过传一个ThreadLocal进来，ThreadLocal中就是这种索引编号。
	 */
	private List<DataSourceWrapper> parse2DataSourceWrapperList(
			String dsWeightCommaStr) {
		logger.info("[parse2DataSourceWrapperList]dsWeightCommaStr="+ dsWeightCommaStr);
		if ((dsWeightCommaStr == null)|| (dsWeightCommaStr = dsWeightCommaStr.trim()).length() == 0) {
			throw new ConfigException("dsWeightCommaStr:'"+ "'对应的配置信息不能为null且长度要大于0");
		}
		return buildDataSourceWrapper(dsWeightCommaStr,new SpringDataSourceFetcher());
	}

	
	
	/**
	 * 将封装好的AtomDataSource的列表，进一步封装为可以根据权重优先级随机选择模板库的DBSelector ---add by
	 * mazhidan.pt
	 * @param dswList
	 */
	private void resetByDataSourceWrapper(List<DataSourceWrapper> dswList) {
		// 删掉已经不存在的DataSourceWrapper
		Map<String, DataSourceWrapper> newDataSourceWrapperMap = new HashMap<String, DataSourceWrapper>(dswList.size());
		
		
		for (DataSourceWrapper dsw : dswList) {
			newDataSourceWrapperMap.put(dsw.getDataSourceKey(), dsw);
		}
		
		Map<String, DataSourceWrapper> old = this.dataSourceWrapperMap;
		this.dataSourceWrapperMap = newDataSourceWrapperMap;
		old.clear();
		old = null;

		DBSelector r_DBSelector = null;
		DBSelector w_DBSelector = null;

		// 如果只有一个db，则用OneDBSelector
		if (dswList.size() == 1) {
			DataSourceWrapper dsw2 = dswList.get(0);
			r_DBSelector = new OneDBSelector(dsw2);
			r_DBSelector.setDbType(dsw2.getDBType());
			w_DBSelector = r_DBSelector;
		} else {
			// 读写优先级Map     将有相同优先级的 数据源放入 一个list里面
			Map<Integer/* 优先级 */, List<DataSourceWrapper>/* 优先级为key的DS 列表 */> rPriority2DswList = new HashMap<Integer, List<DataSourceWrapper>>();
			
			Map<Integer, List<DataSourceWrapper>> wPriority2DswList = new HashMap<Integer, List<DataSourceWrapper>>();
			
			
			for (DataSourceWrapper dsw1 : dswList) {
				add2LinkedListMap(rPriority2DswList, dsw1.getWeight().p, dsw1);// 将有相同优先级的 数据源放入 一个list里面
				add2LinkedListMap(wPriority2DswList, dsw1.getWeight().q, dsw1);// 将有相同优先级的 数据源放入 一个list里面
			}
			
			//创建选择器
			r_DBSelector = createDBSelector(rPriority2DswList, true);
			w_DBSelector = createDBSelector(wPriority2DswList, false);
		}

		r_DBSelector.setReadable(true);
		w_DBSelector.setReadable(false);

		this.readDBSelectorWrapper = r_DBSelector;
		this.writeDBSelectorWrapper = w_DBSelector;

/*		if (tGroupDataSource.getAutoSelectWriteDataSource())
			runtimeWritableAtomDBSelectorWrapper = new RuntimeWritableAtomDBSelector(
					dataSourceWrapperMap, groupExtraConfig);*/
	}
	
	/*
	 * //返回的数组元素个数固定是2，第1个是read，第二个是write private DBSelector[]
	 * createDBSelectors(List<String> dbKeyAndWeightList) { DBSelector
	 * r_DBSelector = null; DBSelector w_DBSelector = null;
	 *
	 * //如果只有一个db，则用OneDBSelector if (dbKeyAndWeightList.size() == 1) { String[]
	 * dbKeyAndWeight = split(dbKeyAndWeightList.get(0), ":"); DataSourceWrapper
	 * dsw = createDataSourceWrapper(dbKeyAndWeight[0], (dbKeyAndWeight.length
	 * == 2 ? dbKeyAndWeight[1] : null), 0); //只有一个数据源时，数据源索引为0
	 *
	 * r_DBSelector = new OneDBSelector(dsw);
	 * r_DBSelector.setDbType(dsw.getDBType()); w_DBSelector = r_DBSelector; }
	 * else { List<List<DataSourceWrapper>> rDataSourceWrappers = new
	 * ArrayList<List<DataSourceWrapper>>(); List<List<DataSourceWrapper>>
	 * wDataSourceWrappers = new ArrayList<List<DataSourceWrapper>>();
	 *
	 * for (int i = 0; i < dbKeyAndWeightList.size(); i++) { String[]
	 * dbKeyAndWeight = split(dbKeyAndWeightList.get(i), ":");
	 *
	 * DataSourceWrapper dsw = createDataSourceWrapper(dbKeyAndWeight[0],
	 * (dbKeyAndWeight.length == 2 ? dbKeyAndWeight[1] : null), i);
	 *
	 * insertSort(rDataSourceWrappers, dsw, true);
	 * insertSort(wDataSourceWrappers, dsw, false); }
	 *
	 * r_DBSelector = createDBSelector(rDataSourceWrappers, true); w_DBSelector
	 * = createDBSelector(wDataSourceWrappers, false); }
	 *
	 * r_DBSelector.setReadable(true); w_DBSelector.setReadable(false);
	 *
	 * return new DBSelector[] { r_DBSelector, w_DBSelector }; }
	 */

	/**
	 * 将给定的k 优先级 加入这个优先级对应的V list 里面。 ----因为可能有多个DS具有相同的优先级 ---add by
	 * mazhidan.pt
	 */
	private static <K, V> void add2LinkedListMap(Map<K, List<V>> m, K key,
			V value) {
		// 从Map中先取出这个优先级的List
		List<V> c = (List<V>) m.get(key);
		// 如果为空，则new一个
		if (c == null) {
			c = new LinkedList<V>();
			m.put(key, c);
		}
		// 不为空，在后面add()
		c.add(value);
	}

	/**
	 * @param dsWeightCommaStr
	 *            : 例如 db0:rwp1q1i0, db1:rwp0q0i1
	 */
	public static List<DataSourceWrapper> buildDataSourceWrapper(String dsWeightCommaStr, DataSourceFetcher fetcher) {
		
		String[] dsWeightArray = dsWeightCommaStr.split(","); // 逗号分隔：db0:rwp1q1i0,// db1:rwp0q0i1
		
		List<DataSourceWrapper> dss = new ArrayList<DataSourceWrapper>(dsWeightArray.length);
		for (int i = 0; i < dsWeightArray.length; i++) {
			String[] dsAndWeight = dsWeightArray[i].split(":"); // 冒号分隔：db0:rwp1q1i0
			String dsKey = dsAndWeight[0].trim();
			String weightStr = dsAndWeight.length == 2 ? dsAndWeight[1] : null;
			// 如果多个group复用一个真实dataSource，会造成所有group引用
			// 这个dataSource的配置 会以最后一个dataSource的配置为准
			DataSource dataSource = fetcher.getDataSource(dsKey);
			DBType fetcherDbType = fetcher.getDataSourceDBType(dsKey);
			// dbType = fetcherDbType == null ? dbType : fetcherDbType;
			DataSourceWrapper dsw = new DataSourceWrapper(dsKey, weightStr,dataSource, fetcherDbType, i);
			dss.add(dsw);
		}
		return dss;
	}

	/**
	 * 根据给定的具有读写优先级及每个优先级对应的DataSource链表的Map，构造DBSelector---add by mazhidan.pt
	 * @param priority2DswList
	 * @param isRead
	 * @return
	 */
	private DBSelector createDBSelector(Map<Integer/* 优先级 */, List<DataSourceWrapper>> priority2DswList,boolean isRead) {
		
		
		if (priority2DswList.size() == 1) { // 只有一个优先级直接使用EquityDbManager
			return createDBSelector2(priority2DswList.entrySet().iterator().next().getValue(), isRead);
		} else {
			List<Integer> priorityKeys = new LinkedList<Integer>();
			
			priorityKeys.addAll(priority2DswList.keySet());
			
			Collections.sort(priorityKeys); // 优先级从小到大排序
			EquityDbManager[] priorityGroups = new EquityDbManager[priorityKeys.size()];
			for (int i = 0; i < priorityGroups.length; i++) { // 最大的优先级放到最前面
				List<DataSourceWrapper> dswList = 
						priority2DswList.get(priorityKeys.get(priorityGroups.length - 1 - i)); // 倒序
				// PriorityDbGroupSelector依赖EquityDbManager抛出的NoMoreDataSourceException来实现，
				// 所以这里即使只有一个ds也只能仍然用EquityDbManager
				priorityGroups[i] = createEquityDbManager(dswList, isRead);
			}
			return new PriorityDbGroupSelector(priorityGroups);
		}
	}

	
	
	private AbstractDBSelector createDBSelector2(List<DataSourceWrapper> dswList, boolean isRead) {
		
		AbstractDBSelector dbSelector;
		if (dswList.size() == 1) {
			DataSourceWrapper dsw = dswList.get(0);
			dbSelector = new OneDBSelector(dsw);
			dbSelector.setDbType(dsw.getDBType());
		} else {
			dbSelector = createEquityDbManager(dswList, isRead);
		}
		return dbSelector;
	}

	
	/*
	 * private DBSelector createDBSelector(List<List<DataSourceWrapper>> list,
	 * boolean isRead) {
	 * int size = list.size(); //优先级别个数 if (size == 1) {
	 * //只有一个优先级直接使用EquityDbManager return createEquityDbManager(list.get(0),
	 * isRead); } else { EquityDbManager[] priorityGroups = new
	 * EquityDbManager[size]; for (int i = 0; i < size; i++) { priorityGroups[i]
	 * = createEquityDbManager(list.get(i), isRead); } return new
	 * PriorityDbGroupSelector(priorityGroups); } }
	 */
	private static EquityDbManager createEquityDbManager(List<DataSourceWrapper> list, boolean isRead) {
		Map<String, DataSourceWrapper> dataSourceMap = new HashMap<String, DataSourceWrapper>(list.size());
		Map<String, Integer> weightMap = new HashMap<String, Integer>(list.size());
		DBType dbType = null;
		for (DataSourceWrapper dsw : list) {
			String dsKey = dsw.getDataSourceKey();
			dataSourceMap.put(dsKey, dsw);
			weightMap.put(dsKey, isRead ? dsw.getWeight().r : dsw.getWeight().w);
			if (dbType == null) {
				dbType = dsw.getDBType();
			}
		}
		EquityDbManager equityDbManager = new EquityDbManager(dataSourceMap,weightMap);
		equityDbManager.setDbType(dbType);
		return equityDbManager;
	}



	/**
	 * 根据当前的读写状态，检查数据源是否可用，数据源分两种：TAtomDataSource和普通的数据源(如DBCP数据源)
	 *
	 * @param ds要检查的数据源
	 * @param isRead
	 *            是对数据源进行读操作(isRead=true)，还是写操作(isRead=false)
	 * @return 普通的数据源不管当前的读写状态是什么，总是可用的，返回true。
	 *         TAtomDataSource如果当前的状态是NA返回false, 否则根据WR状态以及isRead的值决定
	 */
/*	public static boolean isDataSourceAvailable(DataSource ds, boolean isRead) {
		return true;
	}*/

	/**
	 * 不能在TGroupDataSource或TGroupConnection或其他地方把DBSelector做为一个字段保存下来，
	 * 否则db权重配置变了之后无法使用最新的权重配置
	 */
	private volatile DBSelector readDBSelectorWrapper;
	private volatile DBSelector writeDBSelectorWrapper;
	
	//private volatile DBSelector runtimeWritableAtomDBSelectorWrapper;

	/**
	 * 根据是读还是写来选择对应的DBSelector---add by mazhidan.pt
	 */
	public DBSelector getDBSelector(boolean isRead,boolean autoSelectWriteDataSource) {
		DBSelector dbSelector = isRead ? readDBSelectorWrapper: writeDBSelectorWrapper;
		//暂时不支持运行期间主备切换的场景
	/*	if (!isRead && autoSelectWriteDataSource) {
			// 因为所有dbSelector内部的TAtomDataSource都是指向同一个实例，如果某一个TAtomDataSource的状态改了，
			// 那么所有包含这个TAtomDataSource的dbSelector都会知道状态改变了，
			// 所以只要有一个TAtomDataSource的状态变成W，
			// 那么不管这个dbSelector是专门用于读的，还是专门用于写的，也不管是不是runtimeWritableAtomDBSelector，
			// 只要调用了hasWritableDataSource()都会返回true

			// if(!dbSelector.hasWritableDataSource())
			dbSelector = runtimeWritableAtomDBSelectorWrapper;
		}*/
		return dbSelector;
	}




	// 仅用于测试
	public void resetDbGroup(String configInfo) {
		try {
			parse(configInfo);
		} catch (Throwable t) {
			logger.error("resetDbGroup failed:" + configInfo, t);
		}
	}
}