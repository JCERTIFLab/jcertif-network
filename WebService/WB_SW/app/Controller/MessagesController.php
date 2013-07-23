<?php
App::uses('AppController', 'Controller');

class MessagesController extends AppController {

	public function index() {
		$this->Message->recursive = 0;
		$this->set('messages', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->Message->exists($id)) {
			throw new NotFoundException(__('Invalid message'));
		}
		$options = array('conditions' => array('Message.' . $this->Message->primaryKey => $id));
		$this->set('message', $this->Message->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->Message->create();
			if ($this->Message->save($this->request->data)) {
				$this->Session->setFlash(__('The message has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The message could not be saved. Please, try again.'));
			}
		}
		$users = $this->Message->User->find('list');
		$this->set(compact('users'));
	}

	public function edit($id = null) {
		if (!$this->Message->exists($id)) {
			throw new NotFoundException(__('Invalid message'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Message->save($this->request->data)) {
				$this->Session->setFlash(__('The message has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The message could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Message.' . $this->Message->primaryKey => $id));
			$this->request->data = $this->Message->find('first', $options);
		}
		$users = $this->Message->User->find('list');
		$this->set(compact('users'));
	}

	public function delete($id = null) {
		$this->Message->id = $id;
		if (!$this->Message->exists()) {
			throw new NotFoundException(__('Invalid message'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Message->delete()) {
			$this->Session->setFlash(__('Message deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Message was not deleted'));
		$this->redirect(array('action' => 'index'));
	}



	public function getallmessages($id ){
	
	
		$data = $this->Message->find('all', array('recursive' =>-1,'conditions' => array('Message.user_id' => $id)));
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
				$output = json_encode($data);  // transformer les données sous forme json	
			echo $output;
		}
	}
	
	function editmessages($id,$content,$num_sender,$date,$source, $user_id){
	
		$message =$this->Contact->find('all',array('conditions' =>array('Message.id' => $id)));
	
		if (!empty($message)) {
	
			foreach ($message as $m){
	
				$m['Message']['content']=$content;
				$m['Message']['num_sender']=$num_sender;
				$m['Message']['date']=$date;
				$m['Message']['source']=$source;
				$m['Message']['user_id']=$user_id;
	
				if ($this->Message->save($m)) {
					$this->Session->setFlash(__('The Message has been modified', true));
				}
			}
		}
	}
	
	public function ajoutmessages($content,$num_sender,$date,$source, $user_id ){
	
	
		$data= array ('content'=>$content,
				'num_sender'=>$num_sender,
				'date'=>$date,
				'source'=>$source,
				'user_id'=>$user_id);
		$this->layout = 'json';
		if (!empty($data)) {
			$this->Message->create($data);
			if ($this->Message->save($this->data)) {
				$data =$this->Message->find('all',array('recursive' =>-1,'fields' => array('Message.id'),
						'conditions' =>array('Message.num_sender' => $num_sender,'Message.source' => $source,'Message.content'=>$content)));
				$output = json_encode($data);
				echo $output;
			}
	
			else {
			}
		}
	}
	
	function supprimmessages($id){
	
		if ($this->Message->delete($id)) {
			$this->Session->setFlash(__('Message deleted', true));
		}
		else {
			$this->Session->setFlash(__('Message was not deleted', true));
		}
	}
	
	public function parsing(){
		$tab = array();
		$i=0;
		$q="files/read.xml";
		if (file_exists($q)){
	
			$xml = simplexml_load_file($q);
	
			echo $xml->getName() . "<br>";
	
			foreach($xml->children() as $child)
			{
				echo $child->getName() . ": " . $child . "<br>";
					
				foreach($child->children() as $child1)
				{
					echo $child1->getName() . ": " . $child1 . "<br>";
					$tab[$i]= $child1;
					$i=$i+1;
				}
				//debug($data);
				debug($tab) ;
				$data=$this->Message->find('all', array('recursive'=>-1, 'conditions'=>array('Message.nom'=>$tab[0],
						'Message.prenom'=>$tab[1],'Message.email'=>$tab[2],
						'Message.adresse'=>$tab[3],'Message.num_tel'=>$tab[4],
						'Message.num_fax'=>$tab[5])));
				if(!$data){
	
					echo "ajout";
					$this->ajoutmessages($tab[0],$tab[1],$tab[4],$tab[5],$tab[2],$tab[3], $tab[6]);
				}
	
				$i=0;
			}
	
	
		}
	}
	
	public function affichemessage($user_id){
	
		$data = $this->Message->find('all', array('conditions' => array('Message.user_id' => $user_id)));
		$this->set('messages',$data);
	
	}
	





}
