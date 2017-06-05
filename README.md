# HTTPServer
A very simple http server. 

It creates a folder named /Resources/ and looks for an 
index.html file. It is currently vulnerable to a user traversing 
the folder structure using the browser, I have to figure out how to 
sanatize input. 

It currently sends the correct headings for HTML, but not for any 
other file types yet. It does however, send all required files the 
index.html links to. 
