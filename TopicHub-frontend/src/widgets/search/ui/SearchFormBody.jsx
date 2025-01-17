import React from 'react';
import { useForm, Controller } from 'react-hook-form';
import { TextField, Button, Box } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { cleareSearch, getSearchOptions, getSearchState, setSearchOptions } from '../../../features/Search/model/searchSlice';
import { useTranslation } from 'react-i18next';

const SearchFormBody = ({ onSearch }) => {

    const options = useSelector(getSearchOptions)
    const dispatch = useDispatch()
    const empty= useSelector(getSearchState)
    const {t} = useTranslation()

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
        render={({ field }) => <TextField  {...field} label={t('input_theme')} variant="outlined" fullWidth margin="normal" />}
      />
      <Controller
        name="author"
        control={control}
        defaultValue=""
        render={({ field }) => <TextField  {...field} label={t('input_author')} variant="outlined" fullWidth margin="normal" />}
      />
      <Controller
        name="keywords"
        control={control}
        defaultValue=""
        render={({ field }) => <TextField   {...field} label={t('input_keywords')} variant="outlined" fullWidth margin="normal" />}
      />
      <Box display="flex" justifyContent="space-between" marginTop="16px">
        <Button variant="contained" color="primary" type="submit">
          {t('btn_search')}
        </Button>
        <Button variant="outlined" color="secondary" onClick={handleReset}  disabled={empty}>
        {t('btn_reset')}
        </Button>
      </Box>
    </Box>
  );
};

export default SearchFormBody;
