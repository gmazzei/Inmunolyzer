# Inmunolyzer

<img src="Caratula.jpg" height="80%" width="80%">
<br/><br/><br/>

### Guía de instalación

1) OpenCV

<pre>
cd ~
git clone git://github.com/Itseez/opencv.git
cd opencv
mkdir build
cd build
cmake -G "Unix Makefiles" -D CMAKE_CXX_COMPILER=/usr/bin/g++ -D CMAKE_C_COMPILER=/usr/bin/gcc -D WITH_CUDA=ON .. 
make -j4 
make install
</pre>
<br/>

2) MySQL

<pre>
sudo apt-get install mysql-server-5.6
mysql -u root -p
CREATE DATABASE InmunolyzerDB;
use InmunolyzerDB;
<strong>Ahora debemos copiar el contenido de ScriptDB.sql, pegarlo en la terminal y ejecutarlo.</strong>
exit
</pre>

<br/>

3) Clonar repositorio
<pre>
cd ~
git clone git@github.com:gmazzei/Inmunolyzer.git
</pre>
<br/>

6) Reemplazar archivos de OpenCV dentro de Inmunolyzer
<pre>
rm Inmunolyzer/openCV/lib/*.so
rm Inmunolyzer/openCV/repo/syslab-remote/opencv/3.0.0/opencv-300.jar
cp opencv/build/lib/*.so Inmunolyzer/openCV/lib/
cp opencv/build/bin/opencv-300.jar Inmunolyzer/openCV/repo/syslab-remote/opencv/3.0.0/
</pre>
<br/>

7) Levantar Jetty (application server)

<pre>
cd Inmunolyzer
mvn jetty:run
</pre>

<br/>

8) Ingresar desde el browser  
<p>URL: <a href="http://localhost:8080">http://localhost:8080</a></p>
