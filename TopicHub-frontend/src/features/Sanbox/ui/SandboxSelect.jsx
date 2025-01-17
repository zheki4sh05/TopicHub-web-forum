import MainSelect from "../../../shared/Select/ui/MainSelect";
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from 'react-i18next';
import {

  getSandboxComponents,
  saveItem,
} from "../model/sandboxSlice";
function SandboxSelect() {
  const {t} = useTranslation()
  const list = useSelector(getSandboxComponents);
 
  const dispatch = useDispatch();

  const handleChange = (id) => {
    let item = list.find((c) => c.id === id);
    if(item.id!=0){
        dispatch(saveItem({created: new Date().getTime(), ...item}));
    }
   
  };

  return (
    <MainSelect
      list={list}
      title={t('select_title')}
      defaultValue={list[0].id}
      handleChange={handleChange}
      reset={true}
    />
  );
}

export default SandboxSelect;
