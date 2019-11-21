const port = process.env.PORT || 3000;
const http = require('http')
const WebSocket = require('ws');
const express = require('express')
const morgan = require('morgan')
const bodyParser = require('body-parser')
require('log-timestamp');
const app = express()

app.use(morgan('dev'))
app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())

app.use((req, res, next) => {

    res.header('Access-Control-Allow-Origin', '*')
    res.header('Access-Control-Allow-Headers', '*')

    if (req.method === 'OPTIONS') {
        res.header('Access-Control-Allow-Methods', 'PUT, POST, PATCH, DELETE')
        return res.status(200).json({})
    }
    next()
});

app.post('/mock', (req, res, next) => {

    if (isSlaveConnected()) {
        smsSlaveWs.send(JSON.stringify(req.body))
        res.status(200).json({msg: 'SMS sent'})
    } else {
        res.status(500).json({error: "No SMS slave connected, please try again later"})
    }

    return res
});

app.use((req, res, next) => {
    const error = new Error('Not Found')
    error.status = 404
    next(error)
});

app.use((error, req, res, next) => {
    res.status(error.status || 500)
    res.json({
        error: error.message
    })
});

const server = http.createServer(app)
const wsServer = new WebSocket.Server({server});
var smsSlaveWs = null

wsServer.on('connection', ws => {

    console.log("Connected")
    smsSlaveWs = ws

    ws.on('message', message => {
        console.log(`Received ${message} from slave`)
    })
    
    ws.on('close', ws => {
        console.log(`Slave disconnected`)
    })
})

const isSlaveConnected = () => {
    return wsServer.clients.size === 1 && smsSlaveWs;
}

// for debug purposes mostly
setInterval(() => {
    if (isSlaveConnected()) {
        console.log(`SMS slave connected`)
    } else {
        console.log(`No slave connected`)
    }
}, 3000)

server.listen(port, function() {
    console.log('Listening on ' + port)
});
