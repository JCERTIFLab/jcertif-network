<?php
App::uses('AppController', 'Controller');

class FichiersController extends AppController {

	public function index() {
		$this->Fichier->recursive = 0;
		$this->set('fichiers', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->Fichier->exists($id)) {
			throw new NotFoundException(__('Invalid fichier'));
		}
		$options = array('conditions' => array('Fichier.' . $this->Fichier->primaryKey => $id));
		$this->set('fichier', $this->Fichier->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->Fichier->create();
			if ($this->Fichier->save($this->request->data)) {
				$this->Session->setFlash(__('The fichier has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The fichier could not be saved. Please, try again.'));
			}
		}
	}

	public function edit($id = null) {
		if (!$this->Fichier->exists($id)) {
			throw new NotFoundException(__('Invalid fichier'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Fichier->save($this->request->data)) {
				$this->Session->setFlash(__('The fichier has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The fichier could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Fichier.' . $this->Fichier->primaryKey => $id));
			$this->request->data = $this->Fichier->find('first', $options);
		}
	}

	public function delete($id = null) {
		$this->Fichier->id = $id;
		if (!$this->Fichier->exists()) {
			throw new NotFoundException(__('Invalid fichier'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Fichier->delete()) {
			$this->Session->setFlash(__('Fichier deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Fichier was not deleted'));
		$this->redirect(array('action' => 'index'));
	}

	public function upload(){
		 
		 
		debug($this->data);
		 
		$tmp_name=$this->data['Fichier']['filename']['tmp_name'];
		 
		$upload_rep='files/';
		$taille_fichier     = $this->data['Fichier']['filename']['size'];
		$nom_fichier        = $this->data['Fichier']['filename']['name'];
		$extension          = strrchr($this->data['Fichier']['filename']['name'],'.');
		$upload_rep_fichier=$upload_rep.$this->data['Fichier']['filename']['name'];
		//echo $upload_rep.$this->data['Fichier']['filename']['name'];
	
		if(!move_uploaded_file($tmp_name,$upload_rep_fichier)){
			 
		}else{
	
	
			$this->data['Fichier']['nom_fichier'] = $nom_fichier;
			$this->data['Fichier']['url_fichier'] = $upload_rep_fichier;
			$this->data['Fichier']['url_rep'] = $upload_rep;
			$this->data['Fichier']['extension'] = $extension;
			$this->data['Fichier']['taille'] = $taille_fichier;
			$this->Fichier->create($this->data);
			$this->Fichier->save($this->data);
		}
	}

}
