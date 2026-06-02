# SpringAiIntegration

Request:
 POST: http://localhost:8080/ai/analyzeCV
 Body: form-data(key:image,type:file,Value: selected image)
 Prompt: hard-coded in the service layer


 Response: 500 internal server error.


 Explaination: I am running ollama's gemma3:4b model on my local machine. It is extremely slow on my 8gb memory. It does respond quickly to small text based prompts but when i put the CV image(BASE64) and the prompt, CPU & RAM usage skyrockets and after about 8-10 minutes, it returns a 500 error code. 