<!DOCTYPE html>
<html>
	<head>
		<title> Oriental </title>

		<style>
			table, th, td {
			    border: 1px solid black;
			    border-collapse: collapse;
			}
			th, td {
			    padding: 5px;
			    text-align: center;
			}
		</style>
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
			//pilihan algoritma = genetic algorithm
			if(isset($_POST["genetic"])){
				//eksekusi java - push ke array
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

				//buat tabel per-hari
				$arrayOfDay = array("Senin","Selasa","Rabu","Kamis","Jumat");
				echo "<table style='width:100%'>";
				/*header tabel*/
				echo "<tr>";
				echo "<th>Jam\Hari</th>";
				for ($i = 0; $i<5; $i++){
					echo "<th>".$arrayOfDay[$i]."</th>";
				}
				echo "</tr>";

				/*isi per baris*/
				for ($j = 7; $j<19; $j++){
					echo "<tr>";
					echo "<th>".$j; echo ":00 - "; echo $j+1; echo ":00 </th>";
					for ($k = 1; $k<6; $k++){
						echo "<td>";
						for ($i=0; $i < count($arrayOfAct); $i++){
							$tLastStart = $arrayOfAct[$i][2] + $arrayOfAct[$i][3] - 1 ;
							if ($arrayOfAct[$i][1]==$k and $j>=$arrayOfAct[$i][2] and $j<=$tLastStart){
								echo $arrayOfAct[$i][0]." - ".$arrayOfAct[$i][4]."<br>";
							}
						}
						echo "</td>";
					}
					echo "</tr>";
				}
				echo "</table>";
			}

			//pilihan algoritma = hill climbing
			if(isset($_POST["hc"])){
				//eksekusi java - push ke array
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

			//pilihan algoritma = simulated annealing
			if(isset($_POST["sa"])){
				//eksekusi java - push ke array
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