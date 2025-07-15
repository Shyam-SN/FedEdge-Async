import React, { useState, useEffect } from 'react';
import { Activity, Server, Users, GitCommit, CheckCircle, XCircle } from 'lucide-react';
import './index.css';

function App() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  // Poll for events every 2 seconds
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/metrics/events');
        if (response.ok) {
          const data = await response.json();
          setEvents(data);
        }
      } catch (error) {
        console.error('Failed to fetch events:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchEvents();
    const interval = setInterval(fetchEvents, 2000);
    return () => clearInterval(interval);
  }, []);

  const totalModels = events.filter(e => e.eventType === 'MODEL_VERSION_CREATED').length;
  const uniqueClients = new Set(events.map(e => e.clientId)).size;
  const totalAccepted = events.filter(e => e.eventType === 'UPDATE_ACCEPTED').length;
  const totalRejected = events.filter(e => e.eventType.startsWith('UPDATE_REJECTED')).length;

  const getEventIcon = (type) => {
    if (type.includes('ACCEPTED') || type.includes('COMPLETED') || type.includes('CREATED')) {
      return <CheckCircle size={18} className="text-success" color="var(--success)" />;
    }
    if (type.includes('REJECTED')) {
      return <XCircle size={18} className="text-danger" color="var(--danger)" />;
    }
    return <Activity size={18} className="text-accent" color="var(--accent)" />;
  };

  const getBadgeClass = (type) => {
    if (type.includes('ACCEPTED') || type.includes('COMPLETED') || type.includes('CREATED')) return 'badge-success';
    if (type.includes('REJECTED')) return 'badge-danger';
    return 'badge-info';
  };

  return (
    <div className="dashboard-container">
      <header>
        <h1>FedEdge Async Dashboard</h1>
        <div className="glass-panel" style={{ padding: '0.5rem 1rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <div style={{ width: 8, height: 8, borderRadius: '50%', background: 'var(--success)', boxShadow: '0 0 10px var(--success)' }} />
          <span style={{ fontSize: '0.9rem', fontWeight: 600 }}>Live Feed</span>
        </div>
      </header>

      <div className="stats-grid">
        <div className="stat-card glass-panel">
          <div className="stat-title">
            <Server size={20} color="var(--accent)" />
            Global Models
          </div>
          <div className="stat-value">{totalModels}</div>
        </div>
        
        <div className="stat-card glass-panel">
          <div className="stat-title">
            <Users size={20} color="var(--accent)" />
            Active Clients
          </div>
          <div className="stat-value">{uniqueClients}</div>
        </div>

        <div className="stat-card glass-panel">
          <div className="stat-title">
            <CheckCircle size={20} color="var(--success)" />
            Updates Accepted
          </div>
          <div className="stat-value">{totalAccepted}</div>
        </div>

        <div className="stat-card glass-panel">
          <div className="stat-title">
            <XCircle size={20} color="var(--danger)" />
            Updates Rejected
          </div>
          <div className="stat-value">{totalRejected}</div>
        </div>
      </div>

      <div className="main-content">
        <div className="glass-panel" style={{ padding: '1.5rem' }}>
          <div className="panel-header">
            <Activity size={24} color="var(--accent)" />
            Live Training Events
          </div>
          
          {loading ? (
            <div style={{ textAlign: 'center', padding: '3rem', color: 'var(--text-secondary)' }}>
              Loading data from coordinator...
            </div>
          ) : events.length === 0 ? (
            <div style={{ textAlign: 'center', padding: '3rem', color: 'var(--text-secondary)' }}>
              No training events logged yet. Start a client to see data!
            </div>
          ) : (
            <div className="events-list">
              {events.map((event, i) => (
                <div key={event.id || i} className="event-item" style={{ animationDelay: `${i * 0.05}s` }}>
                  <div className="event-details">
                    <div className="event-type">
                      {getEventIcon(event.eventType)}
                      {event.eventType}
                    </div>
                    <div className="event-client">
                      Client: {event.clientId.substring(0, 8)}... | Base Model: v{event.modelVersion || 0}
                    </div>
                  </div>
                  
                  <div className={`badge ${getBadgeClass(event.eventType)}`}>
                    {new Date(event.timestamp).toLocaleTimeString()}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
        
        <div className="glass-panel" style={{ padding: '1.5rem', display: 'flex', flexDirection: 'column' }}>
          <div className="panel-header">
            <GitCommit size={24} color="var(--accent)" />
            System Status
          </div>
          <div style={{ flex: 1, display: 'flex', flexDirection: 'column', justifyContent: 'center', gap: '1rem', color: 'var(--text-secondary)' }}>
            <p><strong>CORS:</strong> Enabled for Localhost</p>
            <p><strong>Policy:</strong> BoundedDecayPolicy</p>
            <p><strong>DB:</strong> PostgreSQL (Connected)</p>
            <div style={{ marginTop: '2rem', padding: '1rem', background: 'rgba(59, 130, 246, 0.1)', borderRadius: '8px', border: '1px solid rgba(59, 130, 246, 0.3)' }}>
              Waiting for clients to send tensors...
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
