# Inmunolyzer

### Guía de instalación

1) OpenCV

UNDER CONSTRUCTION...
<div>
<img src="http://www.gardeningwithmicrobes.com/images/under-construction.jpg" height="62" width="62">
</div>
<br/>

2) MySQL

<pre>
sudo apt-get install mysql-server-5.6
mysql -u root -p
CREATE DATABASE InmunolyzerDB;
use InmunolyzerDB;
</pre>
<p>Ahora debemos copiar el contenido de ScriptDB.sql, pegarlo en la terminal y ejecutarlo.</p>
<pre>
exit
</pre>

<br/>

3) Clonar repositorio
<pre>
cd
git clone git@github.com:gmazzei/Inmunolyzer.git
</pre>
<br/>

6) Copiar archivos de OpenCV
<pre>
rm Inmunolyzer/openCV/lib/*.so
rm Inmunolyzer/openCV/repo/syslab-remote/opencv/3.0.0/opencv-300.jar
cp opencv/build/lib/*.so Inmunolyzer/openCV/lib/
cp opencv/build/bin/opencv-300.jar Inmunolyzer/openCV/repo/syslab-remote/opencv/3.0.0/
</pre>
<br/>

7) Levantar Jetty (application server)

<pre>
cd
cd Inmunolyzer
mvn jetty:run
</pre>

8) Ingresar desde el browser  
<p>URL: <a href="http://localhost:8080">http://localhost:8080</a></p>
