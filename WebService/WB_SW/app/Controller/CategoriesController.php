<?php
App::uses('AppController', 'Controller');

class CategoriesController extends AppController {

	public function index() {
		$this->Category->recursive = 0;
		$this->set('categories', $this->paginate());
	}

	public function view($id = null) {
		if (!$this->Category->exists($id)) {
			throw new NotFoundException(__('Invalid category'));
		}
		$options = array('conditions' => array('Category.' . $this->Category->primaryKey => $id));
		$this->set('category', $this->Category->find('first', $options));
	}

	public function add() {
		if ($this->request->is('post')) {
			$this->Category->create();
			if ($this->Category->save($this->request->data)) {
				$this->Session->setFlash(__('The category has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The category could not be saved. Please, try again.'));
			}
		}
	}

	public function edit($id = null) {
		if (!$this->Category->exists($id)) {
			throw new NotFoundException(__('Invalid category'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->Category->save($this->request->data)) {
				$this->Session->setFlash(__('The category has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The category could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Category.' . $this->Category->primaryKey => $id));
			$this->request->data = $this->Category->find('first', $options);
		}
	}

	public function delete($id = null) {
		$this->Category->id = $id;
		if (!$this->Category->exists()) {
			throw new NotFoundException(__('Invalid category'));
		}
		$this->request->onlyAllow('post', 'delete');
		if ($this->Category->delete()) {
			$this->Session->setFlash(__('Category deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('Category was not deleted'));
		$this->redirect(array('action' => 'index'));
	}

	public function getallcategories($flux_type = null, $id ){
	
	
		$data = $this->Category->find('all', array('recursive' =>-1,'conditions' => array('Category.id' => $id)));
		$this->layout = 'json'; // utiliser la view par defaut de json
	
		if($data){
			if($flux_type=='json'){
	
				$output = json_encode($data);  // transformer les données sous forme json
				header("Content-type: text/x-json"); // ajouter entete de json
			}
			echo $output;
		}
	}
	
	public function ajoutcategories($name){
	
	
		$data= array ('name'=>$name);
		debug($data);
		if (!empty($data)) {
			$this->Category->create($data);
			if ($this->Category->save($this->data)) {
				$this->Session->setFlash(__('The Category has been saved', true));
			}
	
			else {
				$this->Session->setFlash(__('The Category could not be saved. Please, try again.', true));
			}
		}
	}
	
}
