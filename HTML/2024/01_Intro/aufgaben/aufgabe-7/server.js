// das wurde auf glitch.com gehostet und ist nur hier als dokumentation
// auf glitch ist auch der API key gesetzt

const express = require('express');
const https = require('https');
const cors = require('cors');
const multer = require('multer');
const fs = require('fs');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json({limit: '50mb'}));
app.use(express.urlencoded({limit: '50mb', extended: true}));


const upload = multer({ dest: 'uploads/' });

function httpsRequest(options, postData) {
  return new Promise((resolve, reject) => {
    const req = https.request(options, (res) => {
      let data = '';
      res.on('data', (chunk) => {
        data += chunk;
      });
      res.on('end', () => {
        console.log(`Response status code: ${res.statusCode}`);
        console.log(`Response headers: ${JSON.stringify(res.headers)}`);
        console.log(`Response data: ${data}`);
        
        if (res.statusCode >= 200 && res.statusCode < 300) {
          try {
            resolve({ statusCode: res.statusCode, headers: res.headers, data: JSON.parse(data) });
          } catch (error) {
            reject(new Error(`Failed to parse response data: ${error.message}`));
          }
        } else {
          reject(new Error(`Request failed with status code ${res.statusCode}: ${data}`));
        }
      });
    });
    req.on('error', (error) => {
      console.error(`Request error: ${error.message}`);
      reject(error);
    });
    if (postData) {
      req.write(postData);
    }
    req.end();
  });
}

app.post('/analyze-image', upload.single('image'), async (req, res) => {
    try {
        if (!req.file) {
            return res.status(400).json({ error: 'No image file uploaded' });
        }

        const imageBase64 = fs.readFileSync(req.file.path, 'base64');

        const postData = JSON.stringify({
            model: "gpt-4o",  // Updated to use the general GPT-4 model
            messages: [
                {
                    role: "user",
                    content: [
                        { type: "text", text: "Was ist auf dem Bild zu sehen?" },
                        {
                            type: "image_url",
                            image_url: {
                                url: `data:image/jpeg;base64,${imageBase64}`
                            }
                        }
                    ]
                }
            ],
            max_tokens: 300
        });

        const options = {
            hostname: 'api.openai.com',
            port: 443,
            path: '/v1/chat/completions',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${process.env.OPENAI_API_KEY}`,
                'Content-Length': Buffer.byteLength(postData)
            }
        };

        console.log('Sending request to OpenAI API...');
        const response = await httpsRequest(options, postData);
        console.log('OpenAI API Response:', response);

        const description = response.data.choices[0].message.content;

        
        fs.unlinkSync(req.file.path);

        res.json({ description });
    } catch (error) {
        console.error('Error:', error);
        res.status(500).json({ error: 'An error occurred while analyzing the image', details: error.message });
    }
});

app.listen(port, () => {
    console.log(`Server running on port ${port}`);
    console.log(`OPENAI_API_KEY is ${process.env.OPENAI_API_KEY ? 'set' : 'not set'}`);
});