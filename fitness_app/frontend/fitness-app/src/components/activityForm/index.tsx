import { Box, Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import React, { useState } from 'react'
import { addActivity } from '../../services/api';



const ActivityForm = ({onActivitiesAdded}: {onActivitiesAdded: Function}) => {
    const [activity, setActivity] = useState({
        type: "RUNNING", 
        duration: '', 
        caloriesBurned: '',
        additionalMetric:{}
    });

    const handleSubmit = async(e:Event) => {
        e.preventDefault();
        try {
            await addActivity(activity);
            onActivitiesAdded();
            setActivity({type: "RUNNING", duration: '', caloriesBurned: '', additionalMetric:{}});
        } catch (error) {
            console.error(error);
        }
    }
  return (
    <Box 
        component="form" 
        onSubmit={handleSubmit} 
        sx={{mb:4}}
    >
        <FormControl fullWidth sx={{mb: 2}}>
            <InputLabel>Activity Type</InputLabel>
            <Select 
                value={activity.type} 
                onChange={(e) => setActivity({...activity, type: e.target.value})}
            >
                <MenuItem value = "RUNNING">Running</MenuItem>
                <MenuItem value = "WALKING">Walking</MenuItem>
                <MenuItem value = "CYCLING">Cycling</MenuItem>
            </Select>
            <TextField fullWidth
                label='Duration (minutes)'
                type='number'
                sx={{mb:2}}
                value={activity.duration}
                onChange={(e) => setActivity({...activity, duration: e.target.value})}
            />
            <TextField fullWidth
                label='Calories burned'
                type='number'
                sx={{mb:2}}
                value={activity.caloriesBurned}
                onChange={(e) => setActivity({...activity, caloriesBurned: e.target.value})}
            />
            <Button type='submit' variant='contained'>Add Activity</Button>
        </FormControl>
    </Box>
  )
}

export default ActivityForm