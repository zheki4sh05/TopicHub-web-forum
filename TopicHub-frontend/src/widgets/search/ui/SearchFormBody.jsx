import React from 'react';
import { useForm, Controller } from 'react-hook-form';
import { TextField, Button, Box } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { cleareSearch, getSearchOptions, getSearchState, setSearchOptions } from '../../../features/Search/model/searchSlice';
import { getUser, isAuth } from '../../../pages/Profile/model/userSlice';

const SearchFormBody = ({ onSearch }) => {

    const options = useSelector(getSearchOptions)
    const dispatch = useDispatch()
    const empty= useSelector(getSearchState)
   

  const { control, handleSubmit, reset } = useForm({
    defaultValues:{
        theme: options.theme,
        author: options.author,
        keywords: options.keywords
    }
  });

  const onSubmit = (data) => {
    dispatch(setSearchOptions({
      theme: data.theme,
      author: data.author,
      keywords: data.keywords
  }))
    onSearch(data);
    
  };

  const handleReset=()=>{
    reset()
    dispatch(cleareSearch())
  }

  return (
    <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate autoComplete="off">
      <Controller
        name="theme"
        control={control}
        defaultValue=""
        render={({ field }) => <TextField  {...field} label="Тема" variant="outlined" fullWidth margin="normal" />}
      />
      <Controller
        name="author"
        control={control}
        defaultValue=""
        render={({ field }) => <TextField  {...field} label="Имя автора" variant="outlined" fullWidth margin="normal" />}
      />
      <Controller
        name="keywords"
        control={control}
        defaultValue=""
        render={({ field }) => <TextField   {...field} label="Ключевые слова через пробел" variant="outlined" fullWidth margin="normal" />}
      />
      <Box display="flex" justifyContent="space-between" marginTop="16px">
        <Button variant="contained" color="primary" type="submit">
          Поиск
        </Button>
        <Button variant="outlined" color="secondary" onClick={handleReset}  disabled={empty}>
          Сбросить
        </Button>
      </Box>
    </Box>
  );
};

export default SearchFormBody;
