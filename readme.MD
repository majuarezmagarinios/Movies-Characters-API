<h1> Movies Characters API </h1>
<p>  
 <img src="https://img.shields.io/badge/Status-Finished-green" alt="">
 <img src="https://img.shields.io/badge/Version-v1.0-blue" alt="">
</p>

<h3> <u>What does this Rest API do? </u> 🚀</h3>
<h5> It allows to manage entities, persist them and obtain a robust security with Spring Security and JWT</h5>

<h4> Main features: </h4>
<ol>
  <li>
    <b><i>Security</i></b>  
        <p>1.1 Secure user authentication using Spring Security.</p> 
        <p>1.2 Generation and validation of JWT tokens to guarantee authorization. </p>
        <p>1.3 Establishment of roles and privileges for users. </p>
        <p>1.4 Restriction of access to resources according to the roles and privileges assigned. </p>
        <p>1.5 Sending a welcome email to the user upon registration. </p>
  </li>

  <br />

  <li>
    <i><b>Entities:</b></i> Users can create and manage three main entities: Character, Movie, and Genre.
  </li>

  <br />

  <li>
    <i><b>Persistence:</b></i> Entity data is stored in an SQL database for persistence.
  </li>

  <br />

  <li>
    <i><b>Relationships:</b></i> Users have the ability to relate existing or created entities. For example, characters can be
      linked to movies, and the latter to genres.
  </li>

  <br />

  <li>
    <i><b>CRUD Operations:</b></i> The API provides basic create, read, update, and delete (CRUD) operations for parent entities.
  </li>

<br />

  <li>
     <i><b>Advanced Filtering:</b></i> In addition to CRUD operations, the API allows users to filter desired information from the
        database, providing additional functionality to meet their specific needs.
  </li>
</ol>

<br />

<h3> <u>Technologies</u> 🛠️</h3>

<ul>
  <li>Java 17</li>
  <li>Spring Boot 3.0.4 </li>
  <li>JSON Web Tokens </li>
  <li>Project Lombok </li>
  <li>MySQL </li>
  <li>SendGrid - <i>Sending Emails</i> </li>
  <li>OpenAPI 2.1 - <i>Document The API</i> </li>
  <li>Postman - <i>Document The API</i></li>
</ul>

<br />

<h3> <u>Installation and Execution</u> 🔧</h3>

<ol>
   <li>Clone this repository :

   ```shell
   https://github.com/majuarezmagarinios/moviesCharactersAPI.git
   ```
  </li>
  <br />
  <li>In the src\apiDocumentation folder there is a file that you can export to Postman where you will have all the http methods to test</li>
  <br />
  <li>You must create the application key of your email provider. For example for Gmail you can follow this link <a href="https://support.google.com/accounts/answer/185833?hl=es">Link.</a></li>
  <br />
  <li>In the resources/application.properties folder, place the key generated in the previous point after the equals sign, in the property "spring.sendgrid.api-key=" (line 36). Save changes.</li>
  <br />
  <li>Go to services/SendGrid mail Service and on line 22 replace "example@gmail.com" with your mail. Save changes.</li>
  <br />
  <li>You have finished the minimum configuration so that emails are sent to the user when they register.</li>
</ol>

