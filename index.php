<!DOCTYPE html>
<html>
	<head>
		<title> Oriental </title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
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
	
	<body id="top">
		
		<!-- Header -->
			<header id="header">
				<div class="inner">
					<a href="#" class="image avatar"><img src="images/avatar1.jpg" alt="" /></a>
					<h1><strong>Oriental</strong>, sebuah kelompok unik<br />
					beranggotakan mahasiswa IF<br />
					dengan ras Tiong Hoa</h1>
				</div>
				
			</header>
		
		<!-- Main -->
			<div id="main">
				<div>
				<form action="index.php" method="post" >
					<input type="submit" name="genetic" value="Genetic" style="margin-left: 120px"/>
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
								//echo $arrayOfAct[$i][$j]. " ";
							}
							echo "<br>";
						}

						//buat tabel per-hari
						$arrayOfDay = array("Senin","Selasa","Rabu","Kamis","Jumat");
						echo "<table style='width:100%'>";
						echo "<caption> Jadwal Mata Kuliah dan Ruangan</caption>";
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
			</div>
			
		<!-- Footer -->
			<footer id="footer">
				<div class="inner">
					<ul class="copyright">
						<li>&copy; 2016</li><li>Tugas Besar IF3054</li>
					</ul>
				</div>
			</footer>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.poptrox.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="assets/js/main.js"></script>
	</body>

</html>