document.addEventListener('DOMContentLoaded', () => {
    const postsContainer = document.getElementById('posts');
    const postForm = document.getElementById('postForm');
    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');
    const authorEmailInput = document.getElementById('authorEmail');
    const apiUrl = '/api/posts';

    // Fetch and display all posts
    function fetchPosts() {
        fetch(apiUrl)
            .then(response => response.json())
            .then(posts => {
                postsContainer.innerHTML = '';
                posts.forEach(post => {
                    const postElement = document.createElement('div');
                    postElement.className = 'post';
                    postElement.innerHTML = `
                        <div class="post-title">${post.title}</div>
                        <div class="post-content">${post.content}</div>
                        <div class="post-author">작성자: ${post.authorEmail}</div>
                        <div class="post-actions">
                            <button onclick="likePost(${post.id})">Like (${post.likes || 0})</button>
                            <button onclick="showCommentForm(${post.id})">Add Comment</button>
                            <button onclick="deletePost(${post.id})">Delete</button>
                        </div>
                        <div id="comments-${post.id}">
                            ${post.comments ? post.comments.map(comment => `<div class="comment"><p>${comment.content}</p></div>`).join('') : ''}
                        </div>
                        <div id="comment-form-${post.id}" class="comment-form" style="display: none;">
                            <textarea placeholder="댓글을 입력하세요..."></textarea>
                            <button onclick="addComment(${post.id})">Add Comment</button>
                        </div>
                    `;
                    postsContainer.appendChild(postElement);
                });
            })
            .catch(error => console.error('Error fetching posts:', error));
    }

    // Handle form submission
    postForm.addEventListener('submit', event => {
        event.preventDefault();

        const newPost = {
            title: titleInput.value,
            content: contentInput.value,
            authorEmail: authorEmailInput.value
        };

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newPost)
        })
            .then(response => response.json())
            .then(() => {
                titleInput.value = '';
                contentInput.value = '';
                authorEmailInput.value = '';
                fetchPosts();
            })
            .catch(error => console.error('Error creating post:', error));
    });

    // Like a post
    window.likePost = function(id) {
        fetch(`${apiUrl}/${id}/like`, { method: 'POST' })
            .then(() => fetchPosts()) // 좋아요 후 게시물 목록 다시 불러오기
            .catch(error => console.error('Error liking post:', error));
    }

    // Add a comment to a post
    window.addComment = function(postId) {
        const commentText = document.querySelector(`#comment-form-${postId} textarea`).value;

        fetch(`${apiUrl}/${postId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: commentText })
        })
            .then(() => {
                document.querySelector(`#comment-form-${postId} textarea`).value = '';
                fetchPosts(); // 댓글 추가 후 게시물 목록 다시 불러오기
            })
            .catch(error => console.error('Error adding comment:', error));
    }

    // Show comment form
    window.showCommentForm = function(postId) {
        const commentForm = document.querySelector(`#comment-form-${postId}`);
        commentForm.style.display = commentForm.style.display === 'none' ? 'block' : 'none';
    }

    // Delete a post
    window.deletePost = function(id) {
        fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
            .then(() => fetchPosts())
            .catch(error => console.error('Error deleting post:', error));
    }

    // Initial fetch
    fetchPosts();
});

