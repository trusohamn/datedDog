import React, { useEffect, useState } from 'react';
import './App.css';
import Graph from './components/Graph';
import { server } from './constants';
import { Service } from './types';

function App() {
  const [services, setServices] = useState<Service[]>([]);
  const [newName, setNewName] = useState('');
  const [newUrl, setNewUrl] = useState('');

  useEffect(() => {
    let fetchTimeout: NodeJS.Timeout;
    const fetchData = () => {
      fetch(`${server}/services`)
        .then((response) => response.json())
        .then((data) => {
          setServices(data);
          fetchTimeout = setTimeout(fetchData, 2000);
        })
        .catch((e) => {});
    };
    fetchData();
    return () => fetchTimeout && clearTimeout(fetchTimeout);
  }, []);

  const handleAddNewService = (e: React.FormEvent) => {
    e.preventDefault();
    fetch(`${server}/services`, {
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify({ name: newName, url: newUrl }),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  };

  const handleDeleteService = (id: number) => {
    fetch(`${server}/services/${id}`, {
      method: 'DELETE',
      mode: 'cors',
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
          <Graph service={service} onDelete={handleDeleteService} />
        ))}
      </section>
    </div>
  );
}

export default App;
