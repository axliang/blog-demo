/**
 * 
 */
package demo.blog.reentrant2sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ʹ��ReentrantLock���ֽ��������
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-9-27����11:24:12
 */
public class ByteReentrantLock {

	private Lock reentrantLock = new ReentrantLock();
	
	public void test() {
		reentrantLock.lock();
		try {
			
		} catch (Exception e) {
			
		} finally {
			reentrantLock.unlock();
		}
	}
}
