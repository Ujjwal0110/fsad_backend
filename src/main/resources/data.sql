-- Sample seed data for SAMS (password = BCrypt of "Admin@123" and "Student@123")
-- These will only insert if records don't exist

-- Normalize demo credentials on every startup so existing rows stay compatible
-- BCrypt hash below maps to plain text password: "password"
UPDATE users SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' WHERE email = 'admin@sams.edu';
UPDATE users SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' WHERE email = 'alice@student.edu';
UPDATE users SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' WHERE email = 'bob@student.edu';
UPDATE users SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' WHERE email = 'carol@student.edu';

INSERT INTO users (name, email, password, role, department, enrollment_no, phone, created_at)
SELECT 'Admin User', 'admin@sams.edu', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN', 'Administration', NULL, '9876543210', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@sams.edu');

INSERT INTO users (name, email, password, role, department, enrollment_no, phone, created_at)
SELECT 'Alice Johnson', 'alice@student.edu', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'STUDENT', 'Computer Science', 'CS2021001', '9111111111', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'alice@student.edu');

INSERT INTO users (name, email, password, role, department, enrollment_no, phone, created_at)
SELECT 'Bob Smith', 'bob@student.edu', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'STUDENT', 'Electronics', 'EC2021002', '9222222222', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'bob@student.edu');

INSERT INTO users (name, email, password, role, department, enrollment_no, phone, created_at)
SELECT 'Carol Davis', 'carol@student.edu', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'STUDENT', 'Mechanical', 'ME2021003', '9333333333', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'carol@student.edu');

-- Activities (reference admin by subquery)
INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Coding Club', 'Weekly coding sessions and hackathons for tech enthusiasts. Topics include algorithms, DSA, and competitive programming.', 'CLUB', '2026-05-01', '16:00:00', 'Computer Lab 301', 30, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Coding Club');

INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Photography Club', 'Explore photography, editing, and visual storytelling. Open to all skill levels.', 'CLUB', '2026-05-05', '15:00:00', 'Arts Building Room 102', 25, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Photography Club');

INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Football Tournament', 'Inter-department football championship. Form your team and compete!', 'SPORT', '2026-05-10', '09:00:00', 'Main Sports Ground', 100, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Football Tournament');

INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Basketball League', 'Seasonal basketball league with knockout rounds and finals.', 'SPORT', '2026-05-15', '10:00:00', 'Indoor Sports Complex', 60, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Basketball League');

INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Annual Cultural Fest', 'Three-day annual cultural extravaganza featuring music, dance, drama and more!', 'EVENT', '2026-05-20', '10:00:00', 'College Auditorium', 500, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Annual Cultural Fest');

INSERT INTO activities (name, description, category, activity_date, activity_time, location, max_participants, created_by, created_at)
SELECT 'Tech Symposium 2026', 'Annual technology symposium with guest speakers, workshops and project exhibitions.', 'EVENT', '2026-05-25', '09:30:00', 'Seminar Hall A', 200, u.id, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM activities WHERE name = 'Tech Symposium 2026');

-- Sample registrations
INSERT INTO registrations (student_id, activity_id, registration_date, status)
SELECT s.id, a.id, NOW(), 'REGISTERED'
FROM users s, activities a
WHERE s.email = 'alice@student.edu' AND a.name = 'Coding Club'
AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.student_id = s.id AND r.activity_id = a.id);

INSERT INTO registrations (student_id, activity_id, registration_date, status)
SELECT s.id, a.id, NOW(), 'REGISTERED'
FROM users s, activities a
WHERE s.email = 'alice@student.edu' AND a.name = 'Annual Cultural Fest'
AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.student_id = s.id AND r.activity_id = a.id);

INSERT INTO registrations (student_id, activity_id, registration_date, status)
SELECT s.id, a.id, NOW(), 'REGISTERED'
FROM users s, activities a
WHERE s.email = 'bob@student.edu' AND a.name = 'Football Tournament'
AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.student_id = s.id AND r.activity_id = a.id);

INSERT INTO registrations (student_id, activity_id, registration_date, status)
SELECT s.id, a.id, NOW(), 'REGISTERED'
FROM users s, activities a
WHERE s.email = 'carol@student.edu' AND a.name = 'Photography Club'
AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.student_id = s.id AND r.activity_id = a.id);

-- Sample notifications (broadcast)
INSERT INTO notifications (title, message, created_by, target_student_id, is_read, created_at)
SELECT 'Welcome to SAMS!', 'Welcome to the Student Activity Management System. Browse and register for exciting activities!', u.id, NULL, FALSE, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM notifications WHERE title = 'Welcome to SAMS!');

INSERT INTO notifications (title, message, created_by, target_student_id, is_read, created_at)
SELECT 'Cultural Fest Registration Open', 'Registrations for Annual Cultural Fest 2026 are now open. Limited seats available. Register now!', u.id, NULL, FALSE, NOW()
FROM users u WHERE u.email = 'admin@sams.edu'
AND NOT EXISTS (SELECT 1 FROM notifications WHERE title = 'Cultural Fest Registration Open');
