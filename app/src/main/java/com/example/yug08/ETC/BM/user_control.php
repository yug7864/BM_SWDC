<?php

include_once 'connection.php';
header('Content-Type: application/json');

class User
{
    private $db;
    private $connection;

    function __construct()
    {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }

    public function does_user_exist($userID,$userPW)
    {
      $query = "Select * FROM user where id ='$userID' and pw = '$userPW' ";
      $result = mysqli_query($this->connection,$query);

      if(mysqli_num_rows($result)>0)
      {
        $json['success'] = ' Welcome '.$userID;
        echo json_encode($json);
  			mysqli_close($this -> connection);
      }
      else
      {
        $json['error'] = ' ID 혹은 비밀번호를 확인 해주세요.';
        echo json_encode($json);
        mysqli_close($this -> connection);
      }
    }
  }

  $user = new User();
  $userID = $_POST["userID"];
  $userPW = $_POST["userPW"];

  if(empty($userID) && empty($userPW))
  {
      echo json_encode("제발좀....");
  }
  else {
    $userPW = md5($userPW);
    $user -> does_user_exist($userID, $userPW);
  }

 ?>
