<?php
class TutorsController extends AppController {
     public  $name = 'Tutors';
         public  $components = array('RequestHandler');
         
         public function index(){
                $this->layout='default';
         }
         
         public function add(){         
          $this->autoRender=false;
          if($this->RequestHandler->isAjax()){
             Configure::write('debug', 0);
          }
            if(!empty($this->data)){
               if($this->Tutor->save($this->data)){      
                 echo 'Record has been added';
               }else{
                 echo 'Error while adding record';
               }
            }
         }
         
 }
?>