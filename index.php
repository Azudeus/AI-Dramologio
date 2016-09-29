<!DOCTYPE html>
<html>
	<head>
		<title> Oriental </title>
	</head>
	
<?php
	require 'header.php';
	require_once 'header.php';
?>
	
	<body>
		
		<div style="margin-left:30%; width:40%; background-color:#ff0000">
		<form action="index.php" method="post">
			<input type="submit" name="genetic" value="Genetic"/>
			<input type="submit" name="hc" value="Hill Climbing"/>
			<input type="submit" name="sa" value="Simulated Annealing"/>
		</form>
		</div>
		<br><br>
		
		<?php
			if(isset($_POST["genetic"])){
				exec("java -cp ".getcwd()."/src genetic",$arrayOfAct);
				for ($i=0; $i < count($arrayOfAct); $i++){
					$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
				}
				for ($i=0; $i < count($arrayOfAct); $i++){
					for ($j=0; $j < 5; $j++){			
						echo $arrayOfAct[$i][$j]. " ";
					}
					echo "<br>";
				}
			}
			if(isset($_POST["hc"])){
				exec("java -cp ".getcwd()."/src hc",$arrayOfAct);
				for ($i=0; $i < count($arrayOfAct); $i++){
					$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
				}
				for ($i=0; $i < count($arrayOfAct); $i++){
					for ($j=0; $j < 5; $j++){			
						echo $arrayOfAct[$i][$j]. " ";
					}
					echo "<br>";
				}
			}
			if(isset($_POST["sa"])){
				exec("java -cp ".getcwd()."/src sa",$arrayOfAct);
				for ($i=0; $i < count($arrayOfAct); $i++){
					$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
				}
				for ($i=0; $i < count($arrayOfAct); $i++){
					for ($j=0; $j < 5; $j++){			
						echo $arrayOfAct[$i][$j]. " ";
					}
					echo "<br>";
				}
			}
		?>
	</body>

</html>