import { Service } from '../types';

function Graph({
  service,
  onDelete,
}: {
  service: Service;
  onDelete: (id: number) => void;
}) {
  const currentHealthy = service.status[service.status.length - 1];
  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        margin: 10,
        alignItems: 'center',
        backgroundColor: currentHealthy ? 'lightGreen' : 'pink',
        padding: 15,
      }}
    >
      <h2>{service.name}</h2>
      <h3>{service.url}</h3>
      <button onClick={() => onDelete(service.id)}>Delete</button>
      <br />
      <div style={{ display: 'flex' }}>
        {service.status.map((healthy) => (
          <div
            style={{ padding: 10, backgroundColor: healthy ? 'green' : 'red' }}
          ></div>
        ))}
      </div>
    </div>
  );
}

export default Graph;
