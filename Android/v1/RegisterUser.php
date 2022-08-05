<?php
require_once '../includes/DbOperation.php';

$response = array();

    if($_SERVER['REQUEST_METHOD']=='POST'){
        if(isset($_POST['username'])and
            isset($_POST['email'])and
                isset($_POST['password'])){     
                    $db = new DbOperation();

                    $result = $db->createUser(
                        $_POST['username'],
                        $_POST['email'],
                        $_POST['password']);
                    if($result==1){
                        $response['error']= false;
                        $response['message'] = "user register sucessfull";
                    }else if ($result==2){
                        $response['error'] = true;
                        $response['message'] = "Some error occured"; 
                    }else if ($result==0){
                        $response['error'] = true;
                        $response['message'] = "Username and email has already registered"; 
                    }
                }else{
                    $response['error'] = true;
                    $response['message'] = "Required fields are missing"; 
                }
    }else{
        $response['error'] = true;
        $response['message'] = "Invalid Request"; 
    }

    echo json_encode($response);

    
