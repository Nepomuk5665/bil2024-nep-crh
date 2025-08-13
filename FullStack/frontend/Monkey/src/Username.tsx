import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import axios from 'axios';

export default function Username() {

    interface User {
        username: string;
        points: number;
    }

    const handleSubmit = (event) => {

        const user: User = {
            username: event.target.username.value,
            points: 0
        };

        event.preventDefault();
        
        axios.post('http://localhost:8080/api/users', user)
        .then(response => {
            console.log('Response:', response.data);
            alert(`OMG ES HET FUNKTIONIERT`);
        })
        .catch(error => {
            if (error.response.status === 400) {
                alert('Username existiert bereits!');
            }
            console.error('There was an error!', error);
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

      <TextField id="standard-basic" label="Username" variant="standard" name='username' />
    </Box>
  );
}
