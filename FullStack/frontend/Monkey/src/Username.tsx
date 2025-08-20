import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserService from "./UserService"

export default function Username() {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');

    interface User {
        username: string;
        points: number;
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
    
        const user: User = {
            username: username,
            points: 0
        };
        
        if (!user.username) {
            alert('Please enter a username');
            return;
        }
    
        UserService.RegisterUser(user.username)
            .then(() => {
                navigate('/home');
            })
            .catch(error => {
                console.error('Registration failed:', error);
            });
    }
  return (
    <Box
      component="form"
      sx={{ '& > :not(style)': { m: 1, width: '25ch' } }}
      noValidate
      autoComplete="off"
      onSubmit={handleSubmit}
    >

      <TextField id="standard-basic" label="Username" variant="standard" name='username' value={username} onChange={(e) => setUsername(e.target.value)}/>
    </Box>
  );
}
