import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import monkey from '/src/assets/monkey.jpg';
import { Button } from '@mui/material';
import UserService from './UserService';
import banana from '/src/assets/banana.png';

export default function Home() {
    const navigate = useNavigate();
    const [points, setPoints] = React.useState(0);
    const [username, setUsername] = React.useState('');
    const [level, setLevel] = React.useState(1);
    const [label, setLabel] = React.useState('Not enough points to buy a banana');
    const [canBuyBanana, setCanBuyBanana] = React.useState(false);

    const onLoad = () => {
        const username = localStorage.getItem('username');

        if (username) {
            UserService.getUser(username).then(user => {
                if (user) {
                    setUsername(user.username);
                    setPoints(user.points);
                    setLevel(user.level);
                    setLabelText(user.points, user.level);
                }
            }).catch(error => {
                if (error.response && error.response.status === 404) {
                    localStorage.removeItem('username');
                    console.error('User not found:', error);
                    navigate('/login');
                } else {
                    console.error('Error retrieving user:', error);
                    throw error;
                }
            });
        }
    };

    React.useEffect(() => {
        onLoad();
    }, []);

    function buyNewLevel() {
        const requiredPoints = 10 * (level * level + 1);
        if (points >= requiredPoints) {
            UserService.addLevel(username, points - requiredPoints, level + 1)
                .then(updatedUser => {
                    setPoints(updatedUser.points);
                    setLevel(updatedUser.level);
                    setLabel('You bought a banana!');
                    setLabelText(updatedUser.points, updatedUser.level);
                })
                .catch(error => {
                    console.error('Error buying banana:', error);
                });
        }
    }

    function setLabelText(currentPoints: number, currentLevel: number = level) {
        const requiredPoints = 10 * (currentLevel * currentLevel + 1);
        if (currentPoints >= requiredPoints) {
            setLabel('Buy a banana for ' + requiredPoints + ' points');
            setCanBuyBanana(true);
        } else {
            setLabel('Not enough points to buy a banana you need ' + requiredPoints + ' points');
            setCanBuyBanana(false);
        }
    }

    function handleMonkeyClick() {
        const newPoints = points + level;
        
        UserService.addPoints(username, newPoints)
            .then(updatedUser => {
                setPoints(updatedUser.points);
                setLabelText(updatedUser.points, level);
            })
            .catch(error => {
                console.error('Error updating points:', error);
            });
    }
    
    return (
        <>
            <h1>Click on the monkey!</h1>
            <h2>Points: {points}</h2>
            <h2>Level: {level}</h2>
            <h2>{username}</h2>
            <Button 
                variant="contained" 
                disabled={!canBuyBanana} 
                onClick={buyNewLevel}
            >
                {label}
            </Button>
            <div style={{ height: '50px' }}></div>
            <img
                src={monkey}
                alt="Monkey"
                onClick={handleMonkeyClick}
                style={{ cursor: 'pointer', width: '600px', height: '800px' }}
            />
            
            <div style={{ height: '50px' }}></div>
            
            
            
            
                 
            {Array.from({ length: level }, (_, index) => (
                <img
                    key={index}
                    src={banana}
                    alt="Banana"
                    style={{ width: '128px', height: '230px', margin: '10px' }}
                />
            ))}
            
            <div style={{ height: '50px' }}></div>
            
            <Button
                variant="contained"
                title="Logout"
                onClick={() => {
                    localStorage.removeItem('username');
                    navigate('/login');
                }}
            >
                Logout
            </Button>
        </>
    );
}