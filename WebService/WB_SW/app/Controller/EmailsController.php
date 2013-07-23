<?php
App::uses('AppController', 'Controller');

class EmailsController extends AppController {

	public function index() {
		$this->Email->recursive = 0;
		$this->set('emails', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->Email->exists($id)) {
			throw new NotFoundException(__('Invalid email'));
		}
		$options = array('conditions' => array('Email.' . $this->Email->primaryKey => $id));
		$this->set('email', $this->Email->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->Email->create();
			if ($this->Email->save($this->request->data)) {
				$this->Session->setFlash(__('The email has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The email could not be saved. Please, try again.'));
			}
		}
		$users = $this->Email->User->find('list');
		$this->set(compact('users'));
	}

	public function edit($id = null) {
		if (!$this->Email->exists($id)) {
			throw new NotFoundException(__('Invalid email'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Email->save($this->request->data)) {
				$this->Session->setFlash(__('The email has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The email could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Email.' . $this->Email->primaryKey => $id));
			$this->request->data = $this->Email->find('first', $options);
		}
		$users = $this->Email->User->find('list');
		$this->set(compact('users'));
	}

	public function delete($id = null) {
		$this->Email->id = $id;
		if (!$this->Email->exists()) {
			throw new NotFoundException(__('Invalid email'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Email->delete()) {
			$this->Session->setFlash(__('Email deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Email was not deleted'));
		$this->redirect(array('action' => 'index'));
	}

	
	public function getallemails($flux_type = null, $id ){
	
	
		$data = $this->Email->find('all', array('recursive' =>-1,'conditions' => array('Email.id' => $id)));
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				$output = json_encode($data);  // transformer les données sous forme json
				header("Content-type: text/x-json"); // ajouter entete de json
			}
			echo $output;
		}
	}
	
	public function ajoutemails($content,$sender,$object,$date,$source, $user_id ){
	
	
		$data= array ('content'=>$content,
				'sender'=>$sender,
				'object'=>$object,
				'date'=>$date,
				'source'=>$source,
				'user_id'=>$user_id);
		debug($data);
		if (!empty($data)) {
			$this->Email->create($data);
			if ($this->Email->save($this->data)) {
				$this->Session->setFlash(__('The Mail has been saved', true));
			}
	
			else {
				$this->Session->setFlash(__('The Mail could not be saved. Please, try again.', true));
			}
		}
	}
	
	function editemails($id,$content,$sender,$object,$date,$source, $user_id ){
	
		$email =$this->Email->find('all',array('conditions' =>array('Email.id' => $id)));
	
			
		if (!empty($email)) {
	
			foreach ($email as $m){
	
				$m['Email']['content']= $content;
				$m['Email']['sender']=$sender;
				$m['Email']['object']=$object;
				$m['Email']['date']=$date;
				$m['Email']['source']=$source;
				$m['Email']['user_id']=$user_id;
					
				if ($this->Email->save($m)) {
					$this->Session->setFlash(__('The Email has been modified', true));
				}
			}
		}
	}
	
	public function afficheemail($user_id){
	
		$data = $this->Email->find('all', array('conditions' => array('Email.user_id' => $user_id)));
		$this->set('emails',$data);
	
	}
	
	function supprimemails($id){
	
		if ($this->Email->delete($id)) {
			$this->Session->setFlash(__('Email deleted', true));
		}
		else {
			$this->Session->setFlash(__('Email was not deleted', true));
		}
	}
	
	

}
