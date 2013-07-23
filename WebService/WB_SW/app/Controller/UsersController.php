<?php
App::uses('AppController', 'Controller');

class UsersController extends AppController {

	public function index() {
		$this->User->recursive = 0;
		$this->set('users', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->User->exists($id)) {
			throw new NotFoundException(__('Invalid user'));
		}
		$options = array('conditions' => array('User.' . $this->User->primaryKey => $id));
		$this->set('user', $this->User->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->User->create();
			if ($this->User->save($this->request->data)) {
				$this->Session->setFlash(__('The user has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
			}
		}
	}

	public function edit($id = null) {
		if (!$this->User->exists($id)) {
			throw new NotFoundException(__('Invalid user'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->User->save($this->request->data)) {
				$this->Session->setFlash(__('The user has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('User.' . $this->User->primaryKey => $id));
			$this->request->data = $this->User->find('first', $options);
		}
	}

	public function delete($id = null) {
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->User->delete()) {
			$this->Session->setFlash(__('User deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('User was not deleted'));
		$this->redirect(array('action' => 'index'));
	}


	
	function editusers($id,$login,$password,$nom,$prenom,$email){
	
		$user =$this->User->find('all',array('conditions' =>array('User.id' => $id),'recursive'=>-1));
	
		$this->layout = 'json';
		if (!empty($user)) {
	
			foreach ($user as $m){
	
				$m['User']['nom']= $nom;
				$m['User']['prenom']=$prenom;
				$m['User']['login']=$login;
				$m['User']['password']=$password;
				$m['User']['email']=$email;
	
				if ($this->User->save($m)) {
					$output = json_encode($m);
					echo $output;
				}else{
					echo "erreur";
				}
			}
		}
	}
	
	public function ajoutusers($login,$password,$nom,$prenom,$email){
	
		$data= array ('nom'=>$nom,
				'prenom'=>$prenom,
				'login'=>$login,
				'password'=>$password,
				'email'=>$email);
		$this->layout = 'json';
		//debug($data);
		if (!empty($data)) {
			$this->User->create($data);
			if ($this->User->save($this->data)) {
				$data =$this->User->find('all',array('recursive' =>-1,'fields' => array('User.id'),
						'conditions' =>array('User.login' => $login,'User.password' => $password)));
				$output = json_encode($data);
			}
			else {
			}
			echo $output;
		}
	}
	
	public function deleteuser ($id,$cascade = true){
		$this->layout = 'json';
		$this->User->delete($id,true);
		$output = "User Delete";
		echo $output;
			
	}
	
	public function authentification($login,$password){
	
		$data =$this->User->find('all',array('recursive' =>-1,'fields' => array('User.id', 'User.login','User.password','User.nom','User.prenom'),
				'conditions' =>array('User.login' => $login,'User.password' => $password)));
		$this->layout = 'json';
		if($data){
			$output = json_encode($data);
			echo $output;
		}else{}
	}
	
	public function login($flux_type= null,$login,$password){
	
	
		$data =$this->User->find('all',array('recursive' =>-1,
				'conditions' =>
				array('User.login' => $login,'User.password' => $password))
		);
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				$output = json_encode($data);  // transformer les données sous forme json
				header("Content-type: text/x-json"); // ajouter entete de json
	
			}
	
			echo $output;
		}
	}
	
	function logout(){
	}
	
	
	public function getResponse($result = array(), $status = 'OK') {
		$response = array(
				'status' => $status,
				'result' => $result
		);
		return $response;
	}
	
	public function getallusers($flux_type = null ,$id){
	
		//$data = $this->User->find('all', array('recursive' =>-1,));
		$data = $this->User->find('all', array('recursive' =>-1,
				'conditions' => array('User.id' => $id)));//'fields' => array('User.id', 'User.login','User.password'),
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				//$output= var_dump(json_encode($data));
				$output = json_encode($data);  // transformer les données sous forme json
				//	header("Content-type: text/x-json"); // ajouter entete de json
	
			}else{
				App::import('Lib', 'ArrayToXML', array('file' => 'xml'.DS.'ArrayToXML.php'));
	
				$output = ArrayToXML::toXml($this->admin_getResponse($data), 'response');
					
			}
			//$output = json_decode($output, true);
			//var_dump($output);
			echo $output;
		}
	
	}
	
	
	
	
	
}
