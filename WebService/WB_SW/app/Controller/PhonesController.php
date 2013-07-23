<?php
App::uses('AppController', 'Controller');

class PhonesController extends AppController {

	public function index() {
		$this->Phone->recursive = 0;
		$this->set('phones', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->Phone->exists($id)) {
			throw new NotFoundException(__('Invalid phone'));
		}
		$options = array('conditions' => array('Phone.' . $this->Phone->primaryKey => $id));
		$this->set('phone', $this->Phone->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->Phone->create();
			if ($this->Phone->save($this->request->data)) {
				$this->Session->setFlash(__('The phone has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The phone could not be saved. Please, try again.'));
			}
		}
		$users = $this->Phone->User->find('list');
		$this->set(compact('users'));
	}

	public function edit($id = null) {
		if (!$this->Phone->exists($id)) {
			throw new NotFoundException(__('Invalid phone'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Phone->save($this->request->data)) {
				$this->Session->setFlash(__('The phone has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The phone could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Phone.' . $this->Phone->primaryKey => $id));
			$this->request->data = $this->Phone->find('first', $options);
		}
		$users = $this->Phone->User->find('list');
		$this->set(compact('users'));
	}

	public function delete($id = null) {
		$this->Phone->id = $id;
		if (!$this->Phone->exists()) {
			throw new NotFoundException(__('Invalid phone'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Phone->delete()) {
			$this->Session->setFlash(__('Phone deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Phone was not deleted'));
		$this->redirect(array('action' => 'index'));
	}


	public function localisersmartphone($id){
		$data=$this->Phone->find('all',array( 'recursive'=>-1,'conditions' => array('Phone.user_id' =>$id)));
		$this->layout = 'json';	
		if($data){
			$output = json_encode($data);
			echo $output;
		}else{

		}
	}
	
	public function phonereg($reg_id){
		$phone = $this->Phone->find('all',
				array('recursive' =>-1,'fields' => array('Phone.sender'),'conditions' => array('Phone.sender' =>$reg_id)));
		$this->layout = 'json';
		if (!empty($phone)) {
			$output = json_encode($phone);
		}
		else {
		}
		echo $output;
	}
	
	public function sendmessage($id=null){
		$this->set('id',$id);
		if (!empty($this->data)) {
			$message=$this->data['Phone']['message'];
			$phone = $this->Phone->find('all', array('recursive' =>-1,'fields' => array('Phone.sender'),'conditions' => array('Phone.id' =>9)));
			foreach ($phone as $m){
				$sender=$m['Phone']['sender'];
				$registatoin_ids = array($sender);
				$message = array("price" => $message);
	
				$this->send_notification($registatoin_ids,$message);
			}
		}
	}
	
	
	public function fermerphone($id=null,$fermer){
		//$this->set('id',$id);
		$message=$fermer;
		echo $message;
		$phone = $this->Phone->find('all', array('recursive' =>-1,'fields' => array('Phone.sender'),'conditions' => array('Phone.id' =>$id)));
		foreach ($phone as $m){
			$sender=$m['Phone']['sender'];
			$registatoin_ids = array($sender);
			$message = array("price" => $message);
			//$code= array("code"=> $code);
			$this->admin_send_notification($registatoin_ids,$message);
	
		}
		echo"true";
	}
	
	public function send_notification($registatoin_ids, $message) {
	
		$url = 'https://android.googleapis.com/gcm/send';
		$fields = array('registration_ids' => $registatoin_ids,'data' => $message);
		$headers = array('Authorization: key= AIzaSyCvWe4QW2k3sWjCEqK2-tQGgZ8rDyUc__g','Content-Type: application/json');
		// Open connection
		$ch = curl_init();
		// Set the url, number of POST vars, POST data
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_POST, true);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		// Disabling SSL Certificate support temporarly
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
		// Execute post
		$result = curl_exec($ch);
		echo $result;
		if ($result === FALSE) {
			die('Curl failed: ' . curl_error($ch));
		}
		// Close connection
		curl_close($ch);
		echo $result;
	}
	
	public function affichephone($user_id){
	
		$data = $this->Phone->find('all', array('conditions' => array('Phone.user_id' => $user_id)));
		$this->set('phones',$data);
		$id = $this->Phone->find('all', array('fields'=> array('Phone.id'),'conditions' => array('Phone.user_id' => $user_id)));
		foreach ($id as $m){
			$id=$m['Phone']['id'];
			$this->set('id',$id);
		}
	}
	
	public function getallphones($flux_type = null, $id ){
	
	
		$data = $this->Phone->find('all', array('recursive' =>-1,'conditions' => array('Phone.id' => $id)));
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				$output = json_encode($data);  // transformer les données sous forme json
				header("Content-type: text/x-json"); // ajouter entete de json
			}/*else{
			App::import('Libs', 'ArrayToXML', array('file' => 'xml'.DS.'ArrayToXML.php'));
			$output = ArrayToXML::toXml($this->getResponse($data), 'response');
			}*/
	
			echo $output;
		}
	}
	
	public function ajoutphones($model,$type,$os,$sender, $user_id ){
	
	
		$data= array ('model'=>$model,
				'type'=>$type,
				'os'=>$os,
				'sender'=>$sender,
				'user_id'=>$user_id);
		debug($data);
		if (!empty($data)) {
			$this->Phone->create($data);
			if ($this->Phone->save($this->data)) {
				$this->Session->setFlash(__('The Phone has been saved', true));
			}
	
			else {
				$this->Session->setFlash(__('The Phone could not be saved. Please, try again.', true));
			}
		}
	}
	
	function editphones($id,$model,$type,$os,$sender, $user_id){
	
		$phone =$this->Phone->find('all',array('conditions' =>array('Phone.id' => $id)));
	
		if (!empty($phone)) {
	
			foreach ($phone as $m){
	
				$m['Phone']['model']=$model;
				$m['Phone']['type']=$type;
				$m['Phone']['os']=$os;
				$m['Phone']['sender']=$sender;
				$m['Phone']['user_id']=$user_id;
	
				if ($this->Phone->save($m)) {
					$this->Session->setFlash(__('The Phone has been modified', true));
				}
			}
		}
	}
	
	function editlocalisation( $sender,$lat,$lng){
	
		$phone =$this->Phone->find('all',array('conditions' =>array('Phone.sender'=>$sender)));
		
		$this->layout = 'json'; // utiliser la view par defaut de json
		header("Content-type: text/x-json"); // ajouter entete de json
		
		if (!empty($phone)) {
	
			foreach ($phone as $m){
	
				$m['Phone']['lat']=$lat;
				$m['Phone']['lng']=$lng;
				if ($this->Phone->save($m)) {
					$this->Session->setFlash(__('The Phone has been modified', true));
				}
			}
		}
	}
	
	function supprimphones($id){
	
		if ($this->Phone->delete($id)) {
			$this->Session->setFlash(__('Phone deleted', true));
		}
		else {
			$this->Session->setFlash(__('Phone was not deleted', true));
		}
	}
	


}
