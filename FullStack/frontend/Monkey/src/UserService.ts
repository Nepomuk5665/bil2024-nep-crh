import * as React from 'react';
import axios from 'axios';

const baseURL = 'http://localhost:8080/api/users';

function RegisterUser(username: string) {
    const user = {
        username: username,
        points: 0,
        level: 1
    };

    return axios.post(baseURL, user)
        .then(response => {
            console.log('User registered:', response.data);
            localStorage.setItem('username', user.username);
            return response.data;
        })
        .catch(error => {
            if (error.response && error.response.status === 400) {
                return axios.get(`http://localhost:8080/api/users/${user.username}`)
                    .then(response => {
                        console.log('User already exists:', response.data);
                        localStorage.setItem('username', user.username);
                        return response.data;
                    });
            } else {
                console.error('Error registering user:', error);
                throw error;
            }
        });
}

function addPoints(username: string, points: number) {
    return axios.put(`${baseURL}/${username}/points?points=${points}`)
        .then(response => {
            console.log('Points updated:', response.data);
            return response.data;
        })
        .catch(error => {
            console.error('Error updating points:', error);
            throw error;
        });
}

function addLevel(username: string, points: number, level: number) {
    return axios.put(`${baseURL}/${username}`, { points, level })
        .then(response => {
            console.log('Level updated:', response.data);
            return response.data;
        })
        .catch(error => {
            console.error('Error updating Level:', error);
            throw error;
        });
}


function getUser(username: string) {
    return axios.get(`${baseURL}/${username}`)
        .then(response => {
            console.log('User data retrieved:', response.data);
            return {
                username: response.data.username,
                points: response.data.points,
                level: response.data.level
            }
        })
        .catch(error => {
            if (error.response && error.response.status === 404) {
                localStorage.removeItem('username');
                console.error('User not found:', error);
            } else {
                console.error('Error retrieving user:', error);
                throw error;
            }
        });
        
}


export default { RegisterUser, addPoints, getUser, addLevel };