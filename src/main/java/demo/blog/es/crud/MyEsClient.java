/**
 * 
 */
package demo.blog.es.crud;

import org.elasticsearch.client.Client;

/**
 * �Զ����ES Client�ӿڡ�ʵ������ӿڵ���Ҫʵ��
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7����09:34:33
 */
public interface MyEsClient {

	/**
	 * ��ȡ�Ѿ���ʼ����ɵ�{@link Client}���󣬱��ڽ�һ����API���á�
	 * @return �Ѿ���ʼ����ɵ�{@link Client}����
	 */
	Client getEsClient();
	
	/**
	 * �ر����ӡ������Ҫ��ʱ�رգ������ڴ�й¶��
	 */
	void close();
}
