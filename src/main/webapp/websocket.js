let ws;
function connect(){
    const username = document.getElementById("username").value;
    //console.log(username);
    const host = document.location.host; // localhost:8080
    const pathName = document.location.pathname; //  /jvm2_quizz-1.0-SNAPSHOT/
    console.log("host = "+host);
    console.log("pathName = "+pathName);
    console.log("username = "+username)
    console.log("ws://"+host+pathName+"chat/"+username);
    ws = new WebSocket("ws://"+host+pathName+"chat/"+username); // ws://localhost:8080/jvm2_quizz-1.0-SNAPSHOT/chat/ekaterine_gurgenidze
    ws.onmessage=function (event){
        const log = document.getElementById("log");
        console.log("event data "+event.data);
        const message = JSON.parse(event.data);
        console.log("user " + message.from);
        console.log("message content " + message.content);
        log.innerHTML += message.content+"\n";
    }
}

function send(){
    const content = document.getElementById("msg").value;
    const json = JSON.stringify({"content": content});
    console.log(json);
    ws.send(json);
}