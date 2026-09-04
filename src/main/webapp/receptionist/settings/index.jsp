<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - Sunrise Dental Clinic</title>
    <style type="text/tailwindcss">
        @custom-variant dark (&:where(.dark, .dark *));
    </style>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300 min-h-screen">
    
    <%@ include file="../../utils/theme.jsp" %>
    <%@ include file="../includes/receptionist-header.jsp" %>

    <main class="p-8 max-w-6xl mx-auto">
        <div class="mb-8">
            <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Settings</h2>
            <p class="mt-2 text-gray-500 dark:text-gray-400">Manage your profile, security, and preferences.</p>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
            
            <!-- Left Column -->
            <div class="lg:col-span-2 space-y-8">
                
                <!-- Profile Management -->
                <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden">
                    <div class="p-6 border-b border-gray-100 dark:border-gray-700">
                        <h3 class="text-xl font-bold text-gray-800 dark:text-white flex items-center gap-2">
                            <i class="fa-regular fa-user text-emerald-500"></i> Profile Management
                        </h3>
                    </div>
                    <div class="p-6">
                        <form id="profileForm" class="space-y-6">
                            <!-- Full Name (Editable) -->
                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Full Name</label>
                                <input type="text" id="fullName" name="fullName" required
                                    class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition-all">
                            </div>
                            
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Phone (Readonly) -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Phone Number</label>
                                    <div class="relative">
                                        <input type="text" id="phone" disabled
                                            class="block w-full pl-10 pr-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-gray-100 dark:bg-gray-700/50 text-gray-500 dark:text-gray-400 cursor-not-allowed">
                                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <i class="fa-solid fa-phone text-gray-400"></i>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Email (Readonly) -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Email Address</label>
                                    <div class="relative">
                                        <input type="email" id="email" disabled
                                            class="block w-full pl-10 pr-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-gray-100 dark:bg-gray-700/50 text-gray-500 dark:text-gray-400 cursor-not-allowed">
                                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <i class="fa-solid fa-envelope text-gray-400"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Status (Readonly) -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Account Status</label>
                                    <input type="text" id="status" disabled
                                        class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-gray-100 dark:bg-gray-700/50 text-gray-500 dark:text-gray-400 capitalize cursor-not-allowed">
                                </div>
                                <!-- Role (Readonly) -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Role</label>
                                    <input type="text" id="role" disabled
                                        class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-gray-100 dark:bg-gray-700/50 text-gray-500 dark:text-gray-400 capitalize cursor-not-allowed">
                                </div>
                            </div>
                            
                            <div class="flex justify-between items-center pt-4 border-t border-gray-100 dark:border-gray-700">
                                <div class="text-xs text-gray-500 dark:text-gray-400">
                                    <p>Created: <span id="createdAt"></span></p>
                                    <p>Last Updated: <span id="updatedAt"></span></p>
                                </div>
                                <button type="submit" id="saveProfileBtn"
                                    class="px-6 py-2.5 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition shadow-md flex items-center gap-2">
                                    <i class="fa-solid fa-floppy-disk"></i> Save Changes
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Security Management -->
                <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden">
                    <div class="p-6 border-b border-gray-100 dark:border-gray-700">
                        <h3 class="text-xl font-bold text-gray-800 dark:text-white flex items-center gap-2">
                            <i class="fa-solid fa-shield-halved text-emerald-500"></i> Security
                        </h3>
                    </div>
                    <div class="p-6">
                        <form id="passwordForm" class="space-y-6">
                            
                            <div id="passwordError" class="hidden p-4 rounded-xl bg-red-50 text-red-600 dark:bg-red-900/30 dark:text-red-400 text-sm font-medium">
                                <!-- Error msg here -->
                            </div>
                            <div id="passwordSuccess" class="hidden p-4 rounded-xl bg-emerald-50 text-emerald-600 dark:bg-emerald-900/30 dark:text-emerald-400 text-sm font-medium">
                                Password updated successfully!
                            </div>

                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Current Password</label>
                                <input type="password" id="currentPassword" required
                                    class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all">
                            </div>
                            
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">New Password</label>
                                    <input type="password" id="newPassword" required minlength="6"
                                        class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all">
                                </div>
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Confirm New Password</label>
                                    <input type="password" id="confirmPassword" required minlength="6"
                                        class="block w-full px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all">
                                </div>
                            </div>

                            <div class="flex justify-end pt-4">
                                <button type="submit" id="changePasswordBtn"
                                    class="px-6 py-2.5 bg-gray-800 hover:bg-gray-900 dark:bg-gray-100 dark:hover:bg-white dark:text-gray-900 text-white font-medium rounded-xl transition shadow-md flex items-center gap-2">
                                    <i class="fa-solid fa-key"></i> Update Password
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

            <!-- Right Column -->
            <div class="space-y-8">
                
                <!-- Appearance settings -->
                <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden">
                    <div class="p-6 border-b border-gray-100 dark:border-gray-700">
                        <h3 class="text-xl font-bold text-gray-800 dark:text-white flex items-center gap-2">
                            <i class="fa-solid fa-palette text-emerald-500"></i> Appearance
                        </h3>
                    </div>
                    <div class="p-6">
                        <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-4">Theme Preference</label>
                        <div class="relative">
                            <select id="themeSelector" class="block w-full appearance-none px-4 py-3 rounded-xl border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all cursor-pointer">
                                <option value="system">System Default</option>
                                <option value="light">Light Mode</option>
                                <option value="dark">Dark Mode</option>
                            </select>
                            <div class="absolute inset-y-0 right-0 flex items-center px-4 pointer-events-none text-gray-500">
                                <i class="fa-solid fa-chevron-down"></i>
                            </div>
                        </div>
                        <p class="mt-4 text-sm text-gray-500 dark:text-gray-400">
                            Choose how the dashboard looks to reduce eye strain.
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </main>

    <!-- Toast Notification -->
    <div id="toast" class="fixed bottom-5 right-5 transform translate-y-20 opacity-0 transition-all duration-300 bg-emerald-600 text-white px-6 py-3 rounded-xl shadow-lg flex items-center gap-3 z-50">
        <i class="fa-solid fa-check-circle text-xl"></i>
        <span id="toastMsg" class="font-medium">Success!</span>
    </div>

    <script>
        const CONTEXT_PATH = '<%= request.getContextPath() %>';

        function showToast(message) {
            const toast = document.getElementById('toast');
            document.getElementById('toastMsg').textContent = message;
            toast.classList.remove('translate-y-20', 'opacity-0');
            setTimeout(() => {
                toast.classList.add('translate-y-20', 'opacity-0');
            }, 3000);
        }

        function loadProfile() {
            fetch(CONTEXT_PATH + '/SettingsServlet?action=getUserProfile')
                .then(res => {
                    if(!res.ok) throw new Error('Failed to load profile');
                    return res.json();
                })
                .then(data => {
                    document.getElementById('fullName').value = data.full_name;
                    document.getElementById('phone').value = '0' + data.phone;
                    document.getElementById('email').value = data.email || 'N/A';
                    document.getElementById('status').value = data.status;
                    document.getElementById('role').value = data.role;
                    
                    const cDate = new Date(data.created_at);
                    const uDate = new Date(data.updated_at);
                    document.getElementById('createdAt').textContent = isNaN(cDate) ? 'N/A' : cDate.toLocaleString();
                    document.getElementById('updatedAt').textContent = isNaN(uDate) ? 'N/A' : uDate.toLocaleString();
                })
                .catch(err => console.error(err));
        }

        // Initialize Profile
        document.addEventListener('DOMContentLoaded', loadProfile);

        // Update Profile
        document.getElementById('profileForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const btn = document.getElementById('saveProfileBtn');
            const originalText = btn.innerHTML;
            btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Saving...';
            btn.disabled = true;

            const newName = document.getElementById('fullName').value;

            fetch(CONTEXT_PATH + '/SettingsServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ action: 'updateProfile', full_name: newName })
            })
            .then(res => res.json())
            .then(data => {
                btn.innerHTML = originalText;
                btn.disabled = false;
                if(data.success) {
                    showToast('Profile updated successfully!');
                    loadProfile(); // refresh dates
                } else {
                    alert('Error: ' + data.error);
                }
            })
            .catch(err => {
                btn.innerHTML = originalText;
                btn.disabled = false;
                alert('An error occurred.');
            });
        });

        // Change Password
        document.getElementById('passwordForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const errorDiv = document.getElementById('passwordError');
            const successDiv = document.getElementById('passwordSuccess');
            errorDiv.classList.add('hidden');
            successDiv.classList.add('hidden');

            const current = document.getElementById('currentPassword').value;
            const newPass = document.getElementById('newPassword').value;
            const confirm = document.getElementById('confirmPassword').value;

            if (newPass !== confirm) {
                errorDiv.textContent = 'New passwords do not match!';
                errorDiv.classList.remove('hidden');
                return;
            }

            const btn = document.getElementById('changePasswordBtn');
            const originalText = btn.innerHTML;
            btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Updating...';
            btn.disabled = true;

            fetch(CONTEXT_PATH + '/SettingsServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    action: 'changePassword', 
                    current_password: current,
                    new_password: newPass
                })
            })
            .then(res => res.json())
            .then(data => {
                btn.innerHTML = originalText;
                btn.disabled = false;
                if (data.success) {
                    successDiv.classList.remove('hidden');
                    document.getElementById('passwordForm').reset();
                } else {
                    errorDiv.textContent = data.error || 'Failed to update password.';
                    errorDiv.classList.remove('hidden');
                }
            })
            .catch(err => {
                btn.innerHTML = originalText;
                btn.disabled = false;
                errorDiv.textContent = 'An error occurred. Please try again.';
                errorDiv.classList.remove('hidden');
            });
        });

        // Theme Logic
        const themeSelector = document.getElementById('themeSelector');
        
        // Setup initial value
        if (localStorage.theme === 'dark') {
            themeSelector.value = 'dark';
        } else if (localStorage.theme === 'light') {
            themeSelector.value = 'light';
        } else {
            themeSelector.value = 'system';
        }

        themeSelector.addEventListener('change', (e) => {
            const val = e.target.value;
            if (val === 'system') {
                localStorage.removeItem('theme');
                if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
                    document.documentElement.classList.add('dark');
                } else {
                    document.documentElement.classList.remove('dark');
                }
            } else if (val === 'dark') {
                localStorage.theme = 'dark';
                document.documentElement.classList.add('dark');
            } else {
                localStorage.theme = 'light';
                document.documentElement.classList.remove('dark');
            }
        });

    </script>
</body>
</html>
