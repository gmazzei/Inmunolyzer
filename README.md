# Inmunolyzer

Pasos para levantar el proyecto localmente:

1) Clonar el repositorio:

<code>git clone git@github.com:gmazzei/Inmunolyzer.git</code>

2) Ingresar al directorio

<code>cd Inmunolyzer</code>

3) Levantar Jetty

<code>mvn jetty:run</code>

4) Ingresar desde el browser:  

<a href="http://localhost:8080">http://localhost:8080</a>

--


<h4>Notas adicionales</h4>
Pasos para instalar MySQL en Linux:

<code>sudo apt-get install mysql-server-5.6</code>
<br>
<code>mysql -uroot -p</code>
<br>
<code>SET PASSWORD FOR 'xxxx'@'localhost' = PASSWORD('xxxx');</code> (Según el usuario y password que quieran usar)
<br>

Ahora debemos correr el archivo ScriptDB.sql y estaremos listos para levantar el sistema con base de datos.
