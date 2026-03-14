<h1 align="center">URL Shortener System</h1>

<p align="center">
A scalable URL Shortener service built using <b>Java, Spring Boot, Redis, and MySQL</b>.
</p>

<p align="center">
<img src="https://img.shields.io/badge/Java-17-red">
<img src="https://img.shields.io/badge/SpringBoot-3.x-brightgreen">
<img src="https://img.shields.io/badge/Redis-Cache-red">
<img src="https://img.shields.io/badge/MySQL-Database-blue">
<img src="https://img.shields.io/badge/Docker-Container-blue">
</p>

<hr>

<h2>Overview</h2>

<p>
This project implements a <b>URL Shortener system similar to Bitly</b>.  
It demonstrates backend system design concepts such as:
</p>

<ul>
<li>Base62 URL encoding</li>
<li>Distributed ID generation using Snowflake</li>
<li>Redis caching for high-performance redirects</li>
<li>Click analytics tracking</li>
<li>Expiration support for URLs</li>
</ul>

<hr>

<h2>Architecture</h2>

<pre>
Client
  │
  ▼
Spring Boot Application
  │
  ├── Redis (Cache)
  └── MySQL (Database)
        │
        └── url_clicks analytics
</pre>

<hr>

<h2>Tech Stack</h2>

<table>
<tr>
<th>Technology</th>
<th>Purpose</th>
</tr>

<tr>
<td>Java</td>
<td>Backend language</td>
</tr>

<tr>
<td>Spring Boot</td>
<td>REST API framework</td>
</tr>

<tr>
<td>MySQL</td>
<td>Persistent storage</td>
</tr>

<tr>
<td>Redis</td>
<td>High speed caching</td>
</tr>

<tr>
<td>Docker</td>
<td>Containerized infrastructure</td>
</tr>

<tr>
<td>Snowflake Algorithm</td>
<td>Distributed ID generation</td>
</tr>

<tr>
<td>Base62 Encoding</td>
<td>Short URL generation</td>
</tr>

</table>

<hr>

<h2>URL Shortening Flow</h2>

<pre>
User submits long URL
        │
        ▼
POST /api/url/shorten
        │
        ▼
Generate Snowflake ID
        │
        ▼
Base62 Encoding
        │
        ▼
Store in MySQL
        │
        ▼
Return short URL
</pre>

Example:

<pre>
Input:
https://google.com

Output:
http://localhost:8080/a9Kd2P
</pre>

<hr>

<h2>Redirect Flow</h2>

<pre>
User clicks short URL
        │
        ▼
GET /{shortCode}
        │
        ▼
Check Redis Cache
        │
   ┌────┴────┐
   │ Cache   │
   │  Hit    │
   └────┬────┘
        ▼
     Redirect

Cache Miss
     │
     ▼
Query MySQL
     │
     ▼
Store in Redis
     │
     ▼
Redirect User
</pre>

<hr>

<h2>Database Schema</h2>

<h3>urls table</h3>

<table>
<tr>
<th>Column</th>
<th>Description</th>
</tr>

<tr>
<td>id</td>
<td>unique identifier</td>
</tr>

<tr>
<td>short_code</td>
<td>generated Base62 code</td>
</tr>

<tr>
<td>long_url</td>
<td>original URL</td>
</tr>

<tr>
<td>created_at</td>
<td>creation timestamp</td>
</tr>

<tr>
<td>expiration_time</td>
<td>link expiry</td>
</tr>

<tr>
<td>click_count</td>
<td>redirect count</td>
</tr>

</table>

<h3>url_clicks table</h3>

<table>
<tr>
<th>Column</th>
<th>Description</th>
</tr>

<tr>
<td>id</td>
<td>click id</td>
</tr>

<tr>
<td>short_code</td>
<td>short URL</td>
</tr>

<tr>
<td>clicked_at</td>
<td>timestamp</td>
</tr>

<tr>
<td>ip_address</td>
<td>client IP</td>
</tr>

<tr>
<td>user_agent</td>
<td>browser/device</td>
</tr>

</table>

<hr>

<h2>API Endpoints</h2>

<h3>Create Short URL</h3>

<pre>
POST /api/url/shorten
</pre>

Request

<pre>
{
 "longUrl": "https://google.com"
}
</pre>

Response

<pre>
{
 "shortUrl": "http://localhost:8080/a9Kd2P",
 "longUrl": "https://google.com",
 "shortCode": "a9Kd2P"
}
</pre>

<h3>Redirect</h3>

<pre>
GET /{shortCode}
</pre>

Example

<pre>
http://localhost:8080/a9Kd2P
</pre>

Redirects to

<pre>
https://google.com
</pre>

<hr>

<h2>Running the Project</h2>

<h3>1 Clone Repository</h3>

<pre>
git clone https://github.com/byte-ranajit/url-shortener-system.git
cd url-shortener-system
</pre>

<h3>2 Start MySQL using Docker</h3>

<pre>
docker run -d \
--name mysql-db \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=url_shorter_db \
mysql:latest
</pre>

<h3>3 Start Redis</h3>

<pre>
docker run -d \
--name redis-server \
-p 6379:6379 \
redis:latest
</pre>

<h3>4 Run Spring Boot</h3>

<pre>
mvn spring-boot:run
</pre>

<hr>

<h2>Testing API</h2>

<h3>Create Short URL</h3>

<pre>
curl -X POST http://localhost:8080/api/url/shorten \
-H "Content-Type: application/json" \
-d '{"longUrl":"https://google.com"}'
</pre>

<h3>Test Redirect</h3>

<pre>
curl -I http://localhost:8080/a9Kd2P
</pre>

<hr>

<h2>Redis Cache Example</h2>

<pre>
Key       Value
-----------------------------
a9Kd2P -> https://google.com
</pre>

Check keys

<pre>
redis-cli
keys *
</pre>

<hr>

<h2>Project Structure</h2>

<pre>
url-shortener-system
│
├── controller
├── service
├── repository
├── model
├── util
├── config
└── UrlShortenerApplication
</pre>

<hr>

<h2>Future Improvements</h2>

<ul>
<li>Kafka event streaming for analytics</li>
<li>Redis rate limiting</li>
<li>Bloom filters for cache protection</li>
<li>Horizontal scaling with load balancers</li>
<li>CDN based redirects</li>
</ul>

<hr>

<h2>Author</h2>

<p>
<b>Ranajit Khandual</b><br>
Java Backend Developer
</p>
