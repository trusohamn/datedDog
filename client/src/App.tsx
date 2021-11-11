import React, { useEffect, useState } from 'react';
import './App.css';
import Graph from './components/Graph';
import { Service } from './types';

const server = 'http://localhost:8080/services';

function App() {
  const [services, setServices] = useState<Service[]>([]);
  const [newName, setNewName] = useState('');
  const [newUrl, setNewUrl] = useState('');

  useEffect(() => {
    let fetchTimeout: NodeJS.Timeout;
    const fetchData = () => {
      fetch(server)
        .then((response) => response.json())
        .then((data) => {
          setServices(data);
          fetchTimeout = setTimeout(fetchData, 2000);
        });
    };
    fetchData();
    return () => fetchTimeout && clearTimeout(fetchTimeout);
  }, []);

  const handleAddNewService = (e: React.FormEvent) => {
    e.preventDefault();
    fetch(server, {
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify({ name: newName, url: newUrl }),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  };

  return (
    <div className='App'>
      <h1> Monitor</h1>
      <section>
        <h2>Add new service </h2>
        <form onSubmit={handleAddNewService}>
          name{' '}
          <input value={newName} onChange={(e) => setNewName(e.target.value)} />
          <br />
          url{' '}
          <input value={newUrl} onChange={(e) => setNewUrl(e.target.value)} />
          <br />
          <input type='submit' />
        </form>
      </section>
      <section>
        {services.map((service) => (
          <Graph service={service} />
        ))}
      </section>
    </div>
  );
}

export default App;
