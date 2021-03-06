<!DOCTYPE html>
<html>
	<head>
		<title> Oriental </title>
		<!-- frontend -->
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<style> table,th,td,tr {border : 1px solid black; text-align: center}</style>
	</head>

<!-- Header page -->	
<?php
	require 'header.php';
	require_once 'header.php';
?>
	
	<body id="top">
		
		<!-- Fungsi -->
		<?php
			function createTable($arrayOfAct) {
				//buat tabel per-hari
				$arrayOfDay = array("Senin","Selasa","Rabu","Kamis","Jumat");
				echo "<table id='#our_table' border='1' style='width:100%'>";
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
						$dummy1 = 0 ;
						$dummy2 = 0 ;
						for ($i=0; $i < count($arrayOfAct)-2; $i++){
							$tLastStart = $arrayOfAct[$i][2] + $arrayOfAct[$i][3] - 1 ;
							if ($arrayOfAct[$i][1]==$k and $j>=$arrayOfAct[$i][2] and $j<=$tLastStart){
								$dummy1++ ;
								//untuk drag and drop (span)
								if ($dummy1==1){
									echo "<span class='event' id='".$j.$k."' draggable='true'>";
									$dummy2++;
								}
								echo $arrayOfAct[$i][0]." - ".$arrayOfAct[$i][4]."<br>";
							}
						}
						if ($dummy2==1){
							echo "</span>" ;
						}
						echo "</td>";
					}
					echo "</tr>";
				}
				echo "</table>";
			}

			function whiteSpacing($num){
				for ($i=0; $i < $num; $i++){
					echo "&nbsp ";
				}
			}

			function manualMove(){
				echo "<p>";
				echo "Matkul &nbsp &nbsp" ;
				echo "<select class='stepnpop'><option value='IF3130'>IF3130</option><option value='IF2130'>IF2130</option></select>";
				echo "&nbsp &nbsp Ruang &nbsp &nbsp" ;
				echo "<select class='stepnpop'><option value='9602'>9602</option><option value='9601'>9601</option></select>";
				echo "&nbsp &nbsp Hari &nbsp &nbsp" ;
				echo "<select class='stepnpop'><option value='Senin'>Senin</option><option value='Selasa'>Selasa</option></select>";
				echo "&nbsp &nbsp Jam &nbsp &nbsp" ;
				echo "<select class='stepnpop'><option value='7'>7</option><option value='8'>8</option></select>";
				echo "</p>";
				echo "Pindah ke : <br><br>";
				echo "<p>";
				echo "Hari &nbsp &nbsp " ;
				echo "<select class='stepnpop'><option value='Senin'>Senin</option><option value='Selasa'>Selasa</option></select>";
				echo "&nbsp &nbsp Jam &nbsp &nbsp " ;
				echo "<select class='stepnpop'><option value='7'>7</option><option value='8'>8</option></select><br>";
				echo "</p>";
			}
		?>

		<!-- Deskripsi kelompok : pinggir -->
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

				<!-- button pilihan algoritma -->
				<div>
				<form action="index.php" method="post" >
					<input type="submit" name="genetic" value="Genetic" style="margin-left: 120px"/>
					<input type="submit" name="hc" value="Hill Climbing"/>
					<input type="submit" name="sa" value="Simulated Annealing"/><br><br>
					<?php whiteSpacing(20) ?>
					Steps <?php whiteSpacing(2) ?>
					<input type="number" class="stepnpop" name="steps"/>
					<?php whiteSpacing(2) ?>Population/Temperature <?php whiteSpacing(2) ?>
					<input type="number" class="stepnpop" name="population" /><br><br>
				</form>
				</div>
				<br><br>
				
				<!-- input array berdasarkan lagoritma di java -->
				<?php
					//pilihan algoritma = genetic algorithm
					if(isset($_POST["genetic"])){
						//eksekusi java - push ke array
						exec("java -cp ".getcwd()."/src Genetic Testcase.txt ".$_POST['steps']." ".$_POST['population'],$arrayOfAct);
						for ($i=0; $i < count($arrayOfAct)-2; $i++){
							$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
						}

						echo $arrayOfAct[count($arrayOfAct)-2];
						echo "<br>";
						echo $arrayOfAct[count($arrayOfAct)-1];
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						createTable($arrayOfAct);
						echo"<br>";
						/*
						manualMove();
						echo "<br><form method='post' >";
						echo whiteSpacing(25);
						echo "<input type='submit' name='move' value='Move' style='margin-left: 120px'/></form>";
						*/
					}

					if(isset($_POST["move"])){
						createTable($arrayOfAct);
						echo "<input type='submit' name='move' value='Bukan Move' style='margin-left: 120px'/>";
					}

					//pilihan algoritma = hill climbing
					if(isset($_POST["hc"])){
						//eksekusi java - push ke array
						exec("java -cp ".getcwd()."/src Hillclimbing Testcase.txt ".$_POST['steps'],$arrayOfAct);
						for ($i=0; $i < count($arrayOfAct)-2; $i++){
							$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
						}
						echo $arrayOfAct[count($arrayOfAct)-2];
						echo "<br>";
						echo $arrayOfAct[count($arrayOfAct)-1];
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						createTable($arrayOfAct);

					}

					//pilihan algoritma = simulated annealing
					if(isset($_POST["sa"])){
						//eksekusi java - push ke array
						exec("java -cp ".getcwd()."/src SimulatedAnnealing Testcase.txt ".$_POST['steps']." ".$_POST['population']." 0.97",$arrayOfAct);
						for ($i=0; $i < count($arrayOfAct)-2; $i++){
							$arrayOfAct[$i] = preg_split("/[\s,]+/", $arrayOfAct[$i]);
						}
						echo $arrayOfAct[count($arrayOfAct)-2];
						echo "<br>";
						echo $arrayOfAct[count($arrayOfAct)-1];
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						//unset($arrayOfAct[count($arrayOfAct)-1]);
						createTable($arrayOfAct);
					}
				?>
			</div>
			
		<!-- Footer Pinggir -->
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
			<script src="assets/js/main.js"></script>
			<script src="assets/js/dragndrop.js"></script>
	</body>

</html>