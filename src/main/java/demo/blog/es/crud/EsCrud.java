package demo.blog.es.crud;

import org.elasticsearch.action.get.GetResponse;

/**
 * ��ES����˵�CRUD������
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7����10:33:16
 */
public class EsCrud {
	
	private MyEsClient myClient;
	
	/**
	 * ����һ��ʵ����
	 * @param myClient 
	 */
	public EsCrud(MyEsClient myClient) {
		this.myClient = myClient;
	}

	/**
	 * ����ID��ѯһ�����ݼ�¼��
	 * @param id Ҫ��ѯ���ݵ�ID��
	 * @return ���ز�ѯ�����ļ�¼�����json�ַ�����
	 */
	public String get(String id) {
		GetResponse getResponse = this.myClient
									.getEsClient()
									.prepareGet()   // ׼������get��������ʱ����������ִ��get����������ֱ��get������
									.setIndex("student")  // Ҫ��ѯ��
									.setType("student")
									.setId(id)
									.get();
		return getResponse.getSourceAsString();
	}
}
