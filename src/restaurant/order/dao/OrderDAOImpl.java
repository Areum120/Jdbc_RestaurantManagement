package restaurant.order.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import restaurant.order.vo.Order; 


/**
 * 
 * @author SeongJin Park
 *
 */
public class OrderDAOImpl implements OrderDAO{
	
	public static final String FILE_PATH = "src/restaurant/files"; //test
	private String fileName = FILE_PATH+"/order_list";
	ArrayList<Order> ord;
	
	public OrderDAOImpl() {
		ord=new ArrayList<>();
	}
	
	public void start() {
			
		try {
	
			FileInputStream fi = new FileInputStream(fileName);
			BufferedInputStream bi = new BufferedInputStream(fi);
			ObjectInputStream in = new ObjectInputStream(bi);
			
			
			ord = (ArrayList<Order>)in.readObject();
			
			//System.out.println(ord);
			
			 in.close();
		} catch(FileNotFoundException e) {
			
		} catch(EOFException e) {
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void orderSave() {
		try {
			
			FileOutputStream fo = new FileOutputStream(fileName);
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			ObjectOutputStream out = new ObjectOutputStream(bo);
			
			
			out.writeObject(ord);
			out.flush();
			out.close();
			
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void insert(Order orders) {
		try {
		FileInputStream fi = new FileInputStream(fileName);
		BufferedInputStream bi = new BufferedInputStream(fi);
		ObjectInputStream in = new ObjectInputStream(bi);
		
		ArrayList<Order> fileRead = new ArrayList<>();
		fileRead = (ArrayList<Order>)in.readObject();
		
		int max=0;
		for(Order rs : fileRead) { 
			if(rs.getNum()>max) {
				max=rs.getNum();		
			}
		}
		orders.setNum(++max); // 이전 주문 불러와서 주문번호 +1 해주기
		} catch(IOException e){
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		ord.add(orders);
	}
	

	public void delete(int num) {
		
		try {
			FileOutputStream fo = new FileOutputStream(fileName);
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			ObjectOutputStream out = new ObjectOutputStream(bo);
	
	
			
			for(Order rs : ord) {
				if (rs.getNum()==num) {
						ord.remove(rs);
						break;
				}
			}
		
			out.writeObject(ord);
			out.flush();
			out.close();
		
		} catch(IOException e) {
			
		} 

		
	}

	@Override
	public ArrayList<Order> getAllOrder() {
		
		return ord;
	}

	@Override
	public void complete(int num) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fo = new FileOutputStream(fileName);
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			ObjectOutputStream out = new ObjectOutputStream(bo);
	
	
			
			for(Order rs : ord) {
				if (rs.getNum()==num) {
						ord.remove(rs);
						break;
				}
			}
		
			out.writeObject(ord);
			out.flush();
			out.close();
		
		} catch(IOException e) {
			
		} 
		
	}
	
	
	
}
