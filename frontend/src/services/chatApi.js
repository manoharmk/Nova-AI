import axios from 'axios';

/**
 * Sends a user message to the Nova AI backend.
 *
 * @param {string} message - The text query input from the user
 * @returns {Promise<string>} The assistant response reply text
 */
export const sendChatMessage = async (message) => {
  try {
    const response = await axios.post('/api/chat', { message });
    // Expecting response schema: { reply: "..." }
    return response.data.reply;
  } catch (error) {
    console.error('API execution failed:', error);
    if (error.response && error.response.data && error.response.data.message) {
      throw new Error(error.response.data.message);
    }
    throw new Error('Connection failed. Please ensure the backend server is running.');
  }
};
