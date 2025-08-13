import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

export default function Username() {
    const handleSubmit = (event) => {
        event.preventDefault();
        alert('A name was submitted: ' + event.target.username.value);
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
