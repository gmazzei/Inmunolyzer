# Inmunolyzer

INSTALLATION GUIDE UNDER CONSTRUCTION

### Guía de instalación

Requisitos del sistema:
<br/>

1) OpenCV


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
cd opencv/build/
cp lib/*.so ~/Inmunolyzer/openCV/lib/
cp bin/opencv-300.jar ~/Inmunolyzer/openCV/repo/syslab-remote/opencv/3.0.0/
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
