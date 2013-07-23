<?php
App::uses('AppController', 'Controller');

class ContactsController extends AppController {


	public function index() {
		$this->Contact->recursive = 0;
		$this->set('contacts', $this->paginate());
	}


	public function view($id = null) {
		if (!$this->Contact->exists($id)) {
			throw new NotFoundException(__('Invalid contact'));
		}
		$options = array('conditions' => array('Contact.' . $this->Contact->primaryKey => $id));
		$this->set('contact', $this->Contact->find('first', $options));
	}


	public function add() {
		if ($this->request->is('post')) {
			$this->Contact->create();
			if ($this->Contact->save($this->request->data)) {
				$this->Session->setFlash(__('The contact has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The contact could not be saved. Please, try again.'));
			}
		}
		$users = $this->Contact->User->find('list');
		$this->set(compact('users'));
	}


	public function edit($id = null) {
		if (!$this->Contact->exists($id)) {
			throw new NotFoundException(__('Invalid contact'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Contact->save($this->request->data)) {
				$this->Session->setFlash(__('The contact has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The contact could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Contact.' . $this->Contact->primaryKey => $id));
			$this->request->data = $this->Contact->find('first', $options);
		}
		$users = $this->Contact->User->find('list');
		$this->set(compact('users'));
	}


	public function delete($id = null) {
		$this->Contact->id = $id;
		if (!$this->Contact->exists()) {
			throw new NotFoundException(__('Invalid contact'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Contact->delete()) {
			$this->Session->setFlash(__('Contact deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Contact was not deleted'));
		$this->redirect(array('action' => 'index'));
	}


	public function getcontact($flux_type = null ,$id){
	
		//$data = $this->User->find('all', array('recursive' =>-1,));
		$data = $this->Contact->find('all', array('recursive' =>-1,
				'conditions' => array('Contact.id' => $id)));//'fields' => array('User.id', 'User.login','User.password'),
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				//$output= var_dump(json_encode($data));
				$output = json_encode($data);  // transformer les données sous forme json
				//	header("Content-type: text/x-json"); // ajouter entete de json
	
			}else{
				App::import('Lib', 'ArrayToXML', array('file' => 'xml'.DS.'ArrayToXML.php'));
	
				$output = ArrayToXML::toXml($this->getResponse($data), 'response');
					
			}
			//$output = json_decode($output, true);
			//var_dump($output);
			echo $output;
		}
	
	}
	
	function editcontacts($id,$nom,$num_tel,$email,$adresse,$user_id ){
	
		$contact =$this->Contact->find('all',array('conditions' =>array('Contact.id' => $id),'recursive'=>-1));
	
		$this->layout = 'json';
		if (!empty($contact)) {
	
			foreach ($contact as $m){
	
				$m['Contact']['nom']= $nom;
				// $m['Contact']['prenom']=$prenom;
				$m['Contact']['num_tel']=$num_tel;
				//  $m['Contact']['num_fax']=$num_fax;
				$m['Contact']['email']=$email;
				$m['Contact']['adresse']=$adresse;
				$m['Contact']['user_id']=$user_id;
				 
				if ($this->Contact->save($m)) {
					$output = json_encode($m);
					echo $output;
				}else{
					echo "erreur";
				}
			}
		}
	}
	
	public function ajoutcontacts1($nom,$num_tel,$email,$adresse, $user_id ){
	
		$data= array ('nom'=>$nom,
				'num_tel'=>$num_tel,
				'email'=>$email,
				'adresse'=>$adresse,
				'user_id'=>$user_id);
		$this->layout = 'json';
	
		$this->Contact->create($data);
		if ($this->Contact->save($this->data)) {
			$data =$this->Contact->find('all',array('recursive' =>-1,'fields' => array('Contact.id'),
					'conditions' =>array('Contact.nom' => $nom,'Contact.email' => $email)));
			$output = json_encode($data);
			echo $output;
		}
			
		else {
			echo "erreur";
			//$this->Session->setFlash(__('The contact could not be saved. Please, try again.', true));
				
		}
	}
	
	public function ajoutcontacts($nom,$num_tel,$email, $user_id ){
	
		$data= array ('nom'=>$nom,
				'num_tel'=>$num_tel,
				'email'=>$email,
				'user_id'=>$user_id);
		$this->layout = 'json';
	
		$this->Contact->create($data);
		if ($this->Contact->save($this->data)) {
			$data =$this->Contact->find('all',array('recursive' =>-1,'fields' => array('Contact.id'),
					'conditions' =>array('Contact.nom' => $nom,'Contact.email' => $email)));
			$output = json_encode($data);
			echo $output;
		}
			
		else {
			echo "erreur";
			//$this->Session->setFlash(__('The contact could not be saved. Please, try again.', true));
	
		}
	}
	
	function supprimcontacts($id){
	
		if ($this->Contact->delete($id)) {
			$this->Session->setFlash(__('Contact deleted', true));
		}
		else {
			$this->Session->setFlash(__('Contact was not deleted', true));
		}
	}
	
	
	public function affichecontact($user_id){
	
		$data = $this->Contact->find('all', array('conditions' => array('Contact.user_id' => $user_id)));
		$this->set('contacts',$data);
	
	}
	
	public function getallcontacts($id){//$flux_type = null, $id ){
	
		//$data = $this->User->find('all');
		$data = $this->Contact->find('all', array(
				'fields' => array('Contact.id', 'Contact.nom','Contact.prenom','Contact.num_tel','Contact.email','Contact.adresse','Contact.user_id'),
				'conditions' => array('Contact.user_id' => $id)));
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			//if($flux_type=='json'){
	
			$output = json_encode($data);  // transformer les données sous forme json
			//header("Content-type: text/x-json"); // ajouter entete de json
			echo $output;
		}else{
			//App::import('Libs', 'ArrayToXML', array('file' => 'xml'.DS.'ArrayToXML.php'));
				
			/*	echo $this->Xml->serialize($data);
			 $xmlObject = ArrayToXML::toXml($this->)
			fromArray($data);
			$output = $xmlObject->asXML();*/
			//	$output = ArrayToXML::toXml($this->getResponse($data), 'response');
				
			//}
		}
	}
	
	public function parsing1(){
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
				$data=$this->Contact->find('all', array('recursive'=>-1, 'conditions'=>array('Contact.nom'=>$tab[0],
						'Contact.prenom'=>$tab[1],'Contact.email'=>$tab[2],
						'Contact.adresse'=>$tab[3],'Contact.num_tel'=>$tab[4],
						'Contact.num_fax'=>$tab[5],'Contact.user_id'=>$tab[6])));
				if(!$data){
						
					echo "ajout";
					$this->admin_ajoutcontacts($tab[0],$tab[1],$tab[4],$tab[5],$tab[2],$tab[3], $tab[6]);
				}
	
				$i=0;
			}
	
	
		}
	}
	
	public function getResponse($result = array(), $status = 'OK') {
		$response = array(
				'status' => $status,
				'result' => $result
		);
		return $response;
	}
	

}
