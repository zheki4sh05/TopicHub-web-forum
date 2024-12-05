export function formatDateFromTimestamp(timestamp) {
    const date = new Date(timestamp);
    const day = date.getDate();
    const month = date.getMonth() + 1; // Месяцы в JavaScript нумеруются с 0 (январь) до 11 (декабрь)
    const year = date.getFullYear();
  
    // Добавляем впереди ноль, если день или месяц меньше 10
    const formattedDay = day < 10 ? `0${day}` : day;
    const formattedMonth = month < 10 ? `0${month}` : month;
  
    // Собираем дату в нужном формате
    const formattedDate = `${formattedDay}/${formattedMonth}/${year}`;
    return formattedDate;
  }