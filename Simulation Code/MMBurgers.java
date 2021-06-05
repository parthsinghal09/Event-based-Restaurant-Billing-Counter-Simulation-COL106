import java.io.*; 
import java.util.*;

class avl_Node{

	public int key_avl;
	public int h;
	public avl_Node RC;
	public avl_Node LC;
	public int arrival_time;
	public int delivery_time;
	public int status;
	public int nb;
	public int qindex;

	public avl_Node(int id, avl_Node R, avl_Node L, int ta, int no_burgers, int queue_ind){


		key_avl = id;
		RC = R;
		LC = L;

		if(R == null && L == null){
			h = 0;
		} else if(L == null){
			h = 1 + R.h;
		} else if(R == null){
			h = 1 + L.h;
		} else if(R.h<=L.h){
			h = 1 + L.h;
		} else if(R.h>=L.h){
			h = 1 + R.h;
		}

		arrival_time = ta;
		delivery_time = -1;
		status = -1;
		nb = no_burgers;
		qindex = queue_ind; 

	}
}

class qheap_Node{

	public int qlength;
	public int qind;
	
	public qheap_Node(int queuel, int queuei){

		qlength = queuel;
		qind = queuei;

	}

	public boolean Comparatorq(qheap_Node q2){

		boolean b = false;

		if(this.qlength<q2.qlength){
			b = true;
		} else if(this.qlength>q2.qlength){
			b = false;
		} else if(this.qlength == q2.qlength){

			if(this.qind<q2.qind){
				b = true;
			} else if(this.qind>q2.qind){
				b = false;
			}

		}

		return b;

	}
}

class eheap_Node{

	public int time_execution;
	public int event_id;
	public int customer_id;
	public int qid;
	public Vector<Integer> event;

	public eheap_Node(int te, int eid, int cusid, int qi){

		time_execution = te;
		event_id = eid;
		customer_id = cusid;
		qid = qi;
		//after constructing the node using the given event id also add information related to that event into this vector 
		event = new Vector<Integer>();

	}

	public boolean Comparatore(eheap_Node e2){

		// if this<e2 return false else true
		boolean b = false;
		if(this.time_execution<e2.time_execution){

			b = true;

		} else if(this.time_execution>e2.time_execution){

			b = false;

		} else if(this.time_execution == e2.time_execution){

			// event id = 1 -> biling, removal from queue and addition of customer to waiting queue 
			// event id = 2 -> removal of patty from gridle and addition of patty from the queue
			// event id = 3 -> addition of patty to gridle
			// event id = 4 -> burger delivery and removal of customer from waiting queue

			if(this.event_id<e2.event_id){

				b = true;

			} else if(this.event_id>e2.event_id){

				b = false;

			} else {

				if(this.qid>e2.qid){

					b = true;

				} else {

					b = false;

				}

			}

		}

		return b;

	}
}

class minHeape{

	eheap_Node root_heape;
	public Vector<eheap_Node> arrayhe = new Vector<eheap_Node>();

	public minHeape(eheap_Node event_root){

		root_heape = event_root;
		arrayhe.add(root_heape);
	}

	public eheap_Node min_event(){

		return arrayhe.get(0);
	}

	public int length_heape(){

		return arrayhe.size();

	}

	public void insert_heape(eheap_Node e){

		arrayhe.add(e);
		int indexe = arrayhe.size() - 1;
		int p = (indexe - 1)/2;

		while(p>=0 && arrayhe.get(indexe).Comparatore(arrayhe.get(p)) == true){

			eheap_Node etemp = arrayhe.get(indexe);
			arrayhe.set(indexe, arrayhe.get(p));
			arrayhe.set(p, etemp);
			indexe = p;
			p = (p - 1)/2;

		}
	}

	public eheap_Node pop_heape(){

		eheap_Node poppede = arrayhe.get(0);
		arrayhe.set(0, arrayhe.get(arrayhe.size() - 1));
		arrayhe.remove(arrayhe.size() - 1);
		int p = 0;
		
		while(1 == 1){


			if(p>=arrayhe.size()){

				break;

			}

			int left = 2*p + 1;
			int right = 2*p + 2;

			if(left>=arrayhe.size() && right>=arrayhe.size()){

				break;

			} else if (left<arrayhe.size() && right>=arrayhe.size()){

				if(arrayhe.get(left).Comparatore(arrayhe.get(p)) == true){

					eheap_Node etemp = arrayhe.get(left);
					arrayhe.set(left, arrayhe.get(p));
					arrayhe.set(p, etemp);
					p = left;				

				} else{

					break;

				}

			} else if (left<arrayhe.size() && right<arrayhe.size()){

				if(arrayhe.get(left).Comparatore(arrayhe.get(p)) == true){

					if(arrayhe.get(left).Comparatore(arrayhe.get(right)) == true){

						eheap_Node etemp = arrayhe.get(left);
						arrayhe.set(left, arrayhe.get(p));
						arrayhe.set(p, etemp);
						p = left; 

					} else {

						eheap_Node etemp = arrayhe.get(right);
						arrayhe.set(right, arrayhe.get(p));
						arrayhe.set(p, etemp);
						p = right;						

					}

				} else if(arrayhe.get(right).Comparatore(arrayhe.get(p)) == true){

					if(arrayhe.get(right).Comparatore(arrayhe.get(left)) == true){

						eheap_Node etemp = arrayhe.get(right);
						arrayhe.set(right, arrayhe.get(p));
						arrayhe.set(p, etemp);
						p = right; 

					} else {

						eheap_Node etemp = arrayhe.get(left);
						arrayhe.set(left, arrayhe.get(p));
						arrayhe.set(p, etemp);
						p = left;						

					}

				} else {

					break;
					
				}

			}

		}

		return poppede;
	}
}

class minHeapl{

	qheap_Node root_heapq;
	Vector<qheap_Node> arrayhq = new Vector<qheap_Node>();
	Vector<Integer> ind = new Vector<Integer>();

	public minHeapl(qheap_Node q_root, int k){

		root_heapq = q_root;
		arrayhq.add(root_heapq);
		for(int i = 0; i<k; i++){

			ind.add(0);

		}
		
	}

	public qheap_Node min_queue(){

		return arrayhq.get(0);
	}

	public void set_index(qheap_Node q, Integer index){

		ind.set((q.qind - 1), index);

	}

	public int find_pos(Integer qin){

		return ind.get(qin);
	}

	public qheap_Node find_Node(int heap_index){

		return arrayhq.get(heap_index);

	}

	public void insert_heapq(qheap_Node q){

		arrayhq.add(q);
		int indexq = arrayhq.size() - 1;
		set_index(arrayhq.get(indexq), indexq);
		int p = (indexq - 1)/2;

		while(p>=0 && arrayhq.get(indexq).Comparatorq(arrayhq.get(p)) == true){

			qheap_Node qtemp = arrayhq.get(indexq);
			arrayhq.set(indexq, arrayhq.get(p));
			arrayhq.set(p, qtemp);
			set_index(arrayhq.get(p), p);
			set_index(arrayhq.get(indexq), indexq);
			indexq = p;
			p = (p - 1)/2;


		}

	}

	// after updating the length of the root queue delete it from the heap and then again add it after increasing its length
	public qheap_Node pop_heapq(){

		qheap_Node poppedq = arrayhq.get(0);
		set_index(arrayhq.get(0), null);
		arrayhq.set(0, arrayhq.get(arrayhq.size() - 1));
		set_index(arrayhq.get(0), 0);
		arrayhq.remove(arrayhq.size() - 1);
		int p = 0;
		
		while(1 == 1){

			if(p>=arrayhq.size()){

				break;

			}

			int left = 2*p + 1;
			int right = 2*p + 2;

			if(left>=arrayhq.size() && right>=arrayhq.size()){

				break;

			} else if (left<arrayhq.size() && right>=arrayhq.size()){

				if(arrayhq.get(left).Comparatorq(arrayhq.get(p)) == true){

					qheap_Node qtemp = arrayhq.get(left);
					arrayhq.set(left, arrayhq.get(p));
					arrayhq.set(p, qtemp);
					set_index(arrayhq.get(p), p);
					set_index(arrayhq.get(left), left);
					p = left;				

				} else{

					break;

				}

			} else if (left<arrayhq.size() && right<arrayhq.size()){

				if(arrayhq.get(left).Comparatorq(arrayhq.get(p)) == true){

					if(arrayhq.get(left).Comparatorq(arrayhq.get(right)) == true){

						qheap_Node qtemp = arrayhq.get(left);
						arrayhq.set(left, arrayhq.get(p));
						arrayhq.set(p, qtemp);
						set_index(arrayhq.get(p), p);
						set_index(arrayhq.get(left), left);
						p = left; 

					} else if(arrayhq.get(right).Comparatorq(arrayhq.get(left)) == true){

						qheap_Node qtemp = arrayhq.get(right);
						arrayhq.set(right, arrayhq.get(p));
						arrayhq.set(p, qtemp);
						set_index(arrayhq.get(p), p);
						set_index(arrayhq.get(right), right);
						p = right;						

					}

				} else if(arrayhq.get(right).Comparatorq(arrayhq.get(p)) == true){

					if(arrayhq.get(right).Comparatorq(arrayhq.get(left)) == true){

						qheap_Node qtemp = arrayhq.get(right);
						arrayhq.set(right, arrayhq.get(p));
						arrayhq.set(p, qtemp);
						set_index(arrayhq.get(p), p);
						set_index(arrayhq.get(right), right);
						p = right; 

					} else if(arrayhq.get(left).Comparatorq(arrayhq.get(right)) == true){

						qheap_Node qtemp = arrayhq.get(left);
						arrayhq.set(left, arrayhq.get(p));
						arrayhq.set(p, qtemp);
						set_index(arrayhq.get(p), p);
						set_index(arrayhq.get(left), left);
						p = left;						

					}

				} else {

					break;
					
				}

			}

		}

		return poppedq;
		
	} 

	// in the main function first decrease the length of the qheap_Node and then update its position in the heap using this method

	public void update_heapq(qheap_Node q){

		Integer indeq = ind.get(q.qind - 1);
		Integer p = (indeq - 1)/2;

		while(p>=0 && arrayhq.get(indeq).Comparatorq(arrayhq.get(p)) == true){

			qheap_Node qtemp = arrayhq.get(indeq);
			arrayhq.set(indeq, arrayhq.get(p));
			arrayhq.set(p, qtemp);
			set_index(arrayhq.get(indeq), indeq);
			set_index(arrayhq.get(p), p);
			indeq = p;
			p = (p - 1)/2;

		}

	}
}

class avl_Tree{

	avl_Node avl_root;

	public avl_Tree(avl_Node roota){

		avl_root = roota;
	}

	public avl_Node get_root(){

		return avl_root;

	}

	public int height(avl_Node an){

		int ha;
		if(an == null){
			ha = -1;
		} else{
			ha = an.h;
		}
		return ha;
	}

	public void setHeight(avl_Node an){

		if(an == null){
			return; 
		} else{
			int hl = height(an.LC);
			int hr = height(an.RC);
			if(hl>hr){
				an.h = 1 + hl;
			} else{
				an.h = 1 + hr;
			} 
			
		}
	}

	public avl_Node search_avl_node(int key){

		avl_Node navl = search_avl_node1(key, avl_root);
		return navl;
	}

	public avl_Node search_avl_node1(int key, avl_Node avln){


		avl_Node nod = null;

		if(avln.key_avl == key){

			nod = avln;

		} else if(avln.LC!=null && avln.key_avl>key){

			nod = search_avl_node1(key, avln.LC);

		} else if(avln.RC!=null && avln.key_avl<key){

			nod = search_avl_node1(key, avln.RC);

		}
		return nod;
	}

	public void insert_treen(avl_Node avln){

		avl_root = n_insert_treen(avln, avl_root);
	}

	public avl_Node n_insert_treen(avl_Node avln1, avl_Node avln){

		if(avln == null){

			return avln1;

		}else if(avln1.key_avl>avln.key_avl){

			avln.RC = n_insert_treen(avln1, avln.RC);

		} else if(avln1.key_avl<avln.key_avl){

			avln.LC = n_insert_treen(avln1, avln.LC);

		}

		setHeight(avln);

		int b = height(avln.LC) - height(avln.RC);

		if(b == 0 || b == 1 || b == (-1)){

			return avln;

		} else{

			if(b == 2){

				avl_Node Lf = avln.LC;
				avl_Node x1 = Lf.LC;
				avl_Node x2 = Lf.RC;

				if(x2 == null || (x1!=null && x1.h>x2.h)){
					// LL
					avl_Node avlnd = avln;
					avlnd.LC = null;
					avl_Node x = Lf.LC;
					avl_Node lfc = Lf.RC;
					Lf.RC = null;
					avln = Lf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				} else if(x1 == null || (x2!=null && x1.h<x2.h)){
					// LR
					avl_Node x = Lf.RC;
					Lf.RC = null;
					avl_Node xlfc = x.LC;
					x.LC = null;
					avln.LC = x;
					avln.LC.LC = Lf;
					avln.LC.LC.RC = xlfc;
					avl_Node avlnd = avln;
					avl_Node newLf = avlnd.LC;
					avlnd.LC = null;
					avl_Node lfc = newLf.RC;
					newLf.RC = null;
					avln = newLf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				}
				

			}else if(b == (-2)){
				
				avl_Node Rf = avln.RC;
				avl_Node x1 = Rf.LC;
				avl_Node x2 = Rf.RC;
				if(x2 == null || (x1!=null && x1.h>x2.h)){
					// RL
					avl_Node x = Rf.LC;
					Rf.LC = null;
					avl_Node xrfc = x.RC;
					x.RC = null;
					avln.RC = null;
					avln.RC = x;
					avln.RC.RC = Rf;
					avln.RC.RC.LC = xrfc;
					avl_Node avlnd = avln;
					avl_Node newRf = avlnd.RC;
					avlnd.RC = null;
					avl_Node rfc = newRf.LC;
					newRf.LC = null;
					avln = newRf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				} else if(x1 == null || (x2!=null && x1.h<x2.h)){
					// RR
					avl_Node avlnd = avln;
					avlnd.RC = null;
					avl_Node x = Rf.RC;
					avl_Node rfc = Rf.LC;
					Rf.LC = null;
					avln = Rf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);
				}

			}

		}
		return avln;
	}
}

public class MMBurgers implements MMBurgersInterface {

	minHeape event_H;
	minHeapl length_H;
	List<Queue<Integer>> customer_Q;
	Queue<Integer> gridle;
	Queue<Integer> waiting_Customer = new LinkedList<Integer>();
	Queue<Integer> waiting_Patty1 = new LinkedList<Integer>();
	Queue<Integer> waiting_Patty2 = new LinkedList<Integer>();
	Queue<Integer> temp_Patty = new LinkedList<Integer>();
	Queue<Integer> temp_Patty1 = new LinkedList<Integer>();
	avl_Tree customers;
	avl_Node root_avl;
	public int total_customers = 0;
	public int global_Time = 0;
	public int max_gridle_size = 0;
	public int K = 0;

    public boolean isEmpty(){
        //your implementation
        int le;
        if(total_customers == 0){

        	le = 0;

        } else {

        	le = event_H.length_heape();

        }
	    return (le == 0);	
    } 
    
    public void setK(int k) throws IllegalNumberException{
        //your implementation

    	if(K!=0){

    		String s1 = "";
			throw new IllegalNumberException(s1); 

    	}else if(k<=0){

    		String s1 = "";
			throw new IllegalNumberException(s1); 

    	} else {
    		K = k;
		    customer_Q = new ArrayList<Queue<Integer>>(k);
		    customer_Q.add(new LinkedList<Integer>());
		    qheap_Node q1 = new qheap_Node(0, 1);
		    length_H = new minHeapl(q1, k);
		    for(int i = 1; i<k; i++){

			    customer_Q.add(new LinkedList<Integer>());
			    qheap_Node q = new qheap_Node(0, (i + 1));
			    length_H.insert_heapq(q);

			}
		}
    }   
    
    public void setM(int m) throws IllegalNumberException{
        //your implementation
        if(max_gridle_size!=0){

        	String s1 = "";
			throw new IllegalNumberException(s1); 

        }else if(m<=0){

        	String s1 = "";
			throw new IllegalNumberException(s1); 

        } else {

		    max_gridle_size = m;
	    	gridle = new LinkedList<Integer>();

	    }
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
        if(isEmpty()){
        	global_Time = t;
        	return; 
        } 
        if(global_Time>t){

        	String s1 = "";
			throw new IllegalNumberException(s1); 

        } else {
	        while(global_Time<=t){

	        	if(isEmpty()){
	        		global_Time = t;
	        		break;
	        	}
				if(event_H.min_event().time_execution<=t){

					if(global_Time == event_H.min_event().time_execution){

						eheap_Node event_execution = event_H.pop_heape();
			        	if(event_execution.event_id == 1){

			        		int cus_id = event_execution.customer_id;
			        		avl_Node cus_avl = customers.search_avl_node(cus_id);
			        		int index_queue = cus_avl.qindex - 1;
			        		customer_Q.get(index_queue).remove();
			        		int heapl_pos = length_H.find_pos(index_queue);
			        		qheap_Node q_temp = length_H.find_Node(heapl_pos);
			        		q_temp.qlength = q_temp.qlength - 1;
			        		length_H.update_heapq(q_temp);
			        		waiting_Customer.add(cus_id);
			        		cus_avl.status = K + 1;
			        		waiting_Patty1.add(cus_avl.nb);
			        		waiting_Patty2.add(cus_id);
			        		eheap_Node new_event31 = new eheap_Node(global_Time, 3, -1, -1);
			        		event_H.insert_heape(new_event31);
			        		if(customer_Q.get(index_queue).size()>0){

			        			int cus_id3 = customer_Q.get(index_queue).element();
			        			avl_Node cus_avl3 = customers.search_avl_node(cus_id3);
			        			eheap_Node new_event1 = new eheap_Node((global_Time + index_queue + 1), 1, cus_id3, index_queue + 1);
					    		new_event1.event.add(cus_avl3.nb);
					    		event_H.insert_heape(new_event1);

			        		}
						} else if(event_execution.event_id == 3){

							int place_empty = max_gridle_size - griddleState(global_Time);
							while(place_empty>0 && waiting_Patty1.size()>0){

								int nb_temp = waiting_Patty1.element();
								int cus_id = waiting_Patty2.element();
								avl_Node cus_avl = customers.search_avl_node(cus_id);
			        			int index_queue = cus_avl.qindex - 1;
								if(place_empty>=nb_temp){

									for(int i = 0; i<nb_temp; i++){

										gridle.add(cus_id);

									}
									place_empty = place_empty - nb_temp;
									eheap_Node new_event2 = new eheap_Node(global_Time + 10, 2, cus_id, index_queue + 1);
									new_event2.event.add(nb_temp);
									event_H.insert_heape(new_event2);
									waiting_Patty1.remove();
									waiting_Patty2.remove();

								} else {

									int remaining_patty = nb_temp- place_empty;
									for(int i = 0; i<place_empty; i++){

										gridle.add(cus_id);

									}
									int new_nb = (waiting_Patty1.remove() - place_empty);
									temp_Patty.add(new_nb);
									while(waiting_Patty1.size()!=0){
										temp_Patty.add(waiting_Patty1.remove());
									}
									while(temp_Patty.size()!=0){
										waiting_Patty1.add(temp_Patty.remove());
									}
									eheap_Node new_event2 = new eheap_Node(global_Time + 10, 2, cus_id, index_queue + 1);
									new_event2.event.add(place_empty);
									event_H.insert_heape(new_event2);
									place_empty = 0;

								}

							}
							/*if(place_empty>0){

								for(int i = 0; i<nb_temp; i++){

									gridle.add(event_execution.customer_id);

								}
								griddleState(global_Time);
								eheap_Node new_event2 = new eheap_Node(global_Time + 10, 2, event_execution.customer_id, index_queue + 1);
								new_event2.event.add(nb_temp);
								event_H.insert_heape(new_event2);
								

							} else if(place_empty<nb_temp){


								int remaining_patty = nb_temp - place_empty;
								waiting_Patty1.add(remaining_patty);
			        			waiting_Patty2.add(event_execution.customer_id);
			        			for(int i = 0; i<place_empty; i++){

			        				gridle.add(event_execution.customer_id);

			        			}
			        			griddleState(global_Time);
			        			eheap_Node new_event2 = new eheap_Node(global_Time + 10, 2, event_execution.customer_id, index_queue + 1);
			        			new_event2.event.add(place_empty);
								event_H.insert_heape(new_event2);

							}*/
						} else if(event_execution.event_id == 2){

							int cus_id = event_execution.customer_id;
							avl_Node cus_avl = customers.search_avl_node(cus_id);
			        		int index_queue = cus_avl.qindex - 1;
							int del_nb = event_execution.event.get(0);
							for(int i = 0; i<del_nb; i++){

								gridle.remove();

							}
							if(cus_avl.nb == del_nb){

								eheap_Node new_event4 = new eheap_Node(global_Time + 1, 4, event_execution.customer_id, index_queue + 1);
								event_H.insert_heape(new_event4);

							} else {

								cus_avl.nb = cus_avl.nb - del_nb;

							}
							eheap_Node new_event31 = new eheap_Node(global_Time, 3, -1, -1);
			        		event_H.insert_heape(new_event31);
							/*int place_empty = max_gridle_size - griddleState(global_Time);
							if(waiting_Patty1.size()>0){
								while(place_empty!=0 && waiting_Patty1.size()>0){
									if(waiting_Patty1.element()<=place_empty){

										place_empty = place_empty - waiting_Patty1.element();
										int cus_id2 = waiting_Patty2.remove();
					        			avl_Node cus_avl2 = customers.search_avl_node(cus_id2);
					        			int index_queue2 = cus_avl2.qindex - 1;
					        			eheap_Node new_event3 = new eheap_Node(global_Time, 3, cus_id2, index_queue2 + 1);
										new_event3.event.add(waiting_Patty1.remove());
										event_H.insert_heape(new_event3);
										
									} else {

										int cus_id2 = waiting_Patty2.element();
					        			avl_Node cus_avl2 = customers.search_avl_node(cus_id2);
					        			int index_queue2 = cus_avl2.qindex - 1;
					        			eheap_Node new_event3 = new eheap_Node(global_Time, 3, cus_id2, index_queue2 + 1);
										new_event3.event.add(place_empty);
										int new_nb = (waiting_Patty1.remove() - place_empty);
										temp_Patty.add(new_nb);
										while(waiting_Patty1.size()!=0){
											temp_Patty.add(waiting_Patty1.remove());
										}
										while(temp_Patty.size()!=0){
											waiting_Patty1.add(temp_Patty.remove());
										}
										event_H.insert_heape(new_event3);
										place_empty = 0;

									}
								}
							}*/
						} else if(event_execution.event_id == 4){

							int cus_id = event_execution.customer_id;
			        		avl_Node cus_avl = customers.search_avl_node(cus_id);
			        		cus_avl.status = K + 2;
			        		cus_avl.delivery_time = global_Time;
			        		waiting_Customer.remove();
						}

		        	} else if(global_Time<event_H.min_event().time_execution){

		        		global_Time = event_H.min_event().time_execution;

		        	}

				} else {
					global_Time = t;
					break;

				}
	        }
	    }
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
        if(total_customers!=0){

	        avl_Node cus_avl = customers.search_avl_node(id);
		    if(cus_avl!=null){

		    	String s1 = "";
				throw new IllegalNumberException(s1);

		    } 

		} 

	 	if(t>global_Time){

	    	advanceTime(t);
	    	int q_index = (length_H.min_queue().qind - 1);
	    	avl_Node new_customer = new avl_Node(id, null, null, t, numb, (q_index + 1));
	    	if(total_customers == 0){

	    		root_avl = new_customer;
	    		customers = new avl_Tree(root_avl);

	    	} else {

	    		customers.insert_treen(new_customer);

	    	}
	    	total_customers = total_customers + 1;
	    	new_customer.status = q_index + 1;
	    	customer_Q.get(q_index).add(id);
	    	qheap_Node temp = length_H.pop_heapq();
	    	temp.qlength = temp.qlength + 1;
	    	length_H.insert_heapq(temp);
	    	if(customer_Q.get(q_index).size() == 1){

	    		if(total_customers == 1){

	    			eheap_Node new_event1 = new eheap_Node((t + q_index + 1), 1, id, q_index + 1);
	    			new_event1.event.add(numb);
	    			event_H = new minHeape(new_event1);

	    		} else {

		    		eheap_Node new_event1 = new eheap_Node((t + q_index + 1), 1, id, q_index + 1);
		    		new_event1.event.add(numb);
		    		event_H.insert_heape(new_event1);
		    	}

	    	}

	    } else if(t == global_Time){

	    	int q_index = (length_H.min_queue().qind - 1);
	    	avl_Node new_customer = new avl_Node(id, null, null, t, numb, (q_index + 1));
	    	if(total_customers == 0){
	    		root_avl = new_customer;
	    		customers = new avl_Tree(root_avl);
	    	} else {

	    		customers.insert_treen(new_customer);

	    	}
	    	total_customers = total_customers + 1;
	    	new_customer.status = q_index + 1;
	    	customer_Q.get(q_index).add(id);
	    	qheap_Node temp = length_H.pop_heapq();
	    	temp.qlength = temp.qlength + 1;
	    	length_H.insert_heapq(temp);
	    	if(customer_Q.get(q_index).size() == 1){

	    		if(total_customers == 1){

	    			eheap_Node new_event1 = new eheap_Node((t + q_index + 1), 1, id, q_index + 1);
	    			new_event1.event.add(numb);
	    			event_H = new minHeape(new_event1);

	    		} else {

		    		eheap_Node new_event1 = new eheap_Node((t + q_index + 1), 1, id, q_index + 1);
		    		new_event1.event.add(numb);
		    		event_H.insert_heape(new_event1);
		    	}

	    	}

	    } else {

	    	String s1 = "";
			throw new IllegalNumberException(s1); 

		}
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
	  
	    int s = -1;
	    if(total_customers!=0){

	    	avl_Node cus_avl = customers.search_avl_node(id);
		    if(cus_avl == null){

		    	s = 0;
		    	return s;

		    }

		    if(global_Time<t){

		    	advanceTime(t);
		    	s = cus_avl.status;

		    } else if(global_Time == t){

		    	s = cus_avl.status;

		    } else {

				String s1 = "";
				throw new IllegalNumberException(s1);    		

			}

	    } else if(total_customers == 0){

	    	s = 0;
		    return s;

	    }
	    
	    return s;
    } 

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
    	int state;
    	if(global_Time<t){

    		advanceTime(t);
    		state = gridle.size();
    		

    	} else if(global_Time == t){

    		state = gridle.size();
    		

    	} else {

			String s = "";
			throw new IllegalNumberException(s);    		

    	}

	    return state;
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation

	    int w = 0;
    	if(global_Time<t){

    		advanceTime(t);
    		while(waiting_Patty1.size()!=0){

    			w = w + waiting_Patty1.element();
    			temp_Patty1.add(waiting_Patty1.remove());

    		}
    		while(temp_Patty1.size()!=0){

    			waiting_Patty1.add(temp_Patty1.remove());

    		}
    		 

    	} else if(global_Time == t) {

    		
    		while(waiting_Patty1.size()!=0){

    			w = w + waiting_Patty1.element();
    			temp_Patty1.add(waiting_Patty1.remove());

    		}
    		while(temp_Patty1.size()!=0){

    			waiting_Patty1.add(temp_Patty1.remove());

    		}

    	} else {

			String s = "";
			throw new IllegalNumberException(s);    		

    	}

	    return w;
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation

    	int w_time = 0;
	    avl_Node cus_avl = customers.search_avl_node(id);
	    if(cus_avl == null){

	    	String s = "";
			throw new IllegalNumberException(s);

	    } else {
	    	w_time = (cus_avl.delivery_time - cus_avl.arrival_time);
	    }
	    return w_time;	
    }

    public int avgWaitTime_helper(avl_Node avln){

    	int time = (avln.delivery_time - avln.arrival_time);

    	if(avln.LC == null && avln.RC == null){

    		return time;

    	} else if(avln.LC == null && avln.RC!=null){

    		time = time + avgWaitTime_helper(avln.RC);

    	} else if(avln.RC == null && avln.LC!=null){

    		time = time + avgWaitTime_helper(avln.LC);

    	} else {

    		time = time + avgWaitTime_helper(avln.LC);
    		time = time + avgWaitTime_helper(avln.RC);

    	}

    	return time;
    } 

	public float avgWaitTime(){
        //your implementation
        avl_Node new_root_avl = customers.get_root();
	    float avg_time = (float) ((float) avgWaitTime_helper(new_root_avl))/((float) total_customers);
	    return avg_time;
    }     
}
