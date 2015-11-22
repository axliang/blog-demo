/**
 * 
 */
package demo.blog.es.crud;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;

/**
 * ʹ��{@link TransportClient}��Ϊ����ES API��Client����ʹ��{@link #getEsClient()}�������س�ʼ����ɵĶ���
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7����09:52:25
 */
public class MyTransportClient implements MyEsClient {

	private TransportClient transportClient;
	
	/**
	 * ����{@link TransportClient}�������ӵ�ַΪ<code>localhost:9300</code>��ES����ˡ�
	 */
	public MyTransportClient() {
		
		// ����
		Settings esSetting = settingsBuilder()
								.put("cluster.name", "elasticsearch")
								.build();
		transportClient = new TransportClient(esSetting);
		
		// ������ӵ�ַ
		TransportAddress address = new InetSocketTransportAddress("192.168.1.110", 9300);
		transportClient.addTransportAddress(address);
		
		
	}

	public Client getEsClient() {
		return this.transportClient;
	}

	public void close() {
		this.transportClient.close();
	}
}
