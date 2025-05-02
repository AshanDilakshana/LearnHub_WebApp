import { useEffect, useState } from 'react';
import { getLearningPlans, createLearningPlan, updateLearningPlan, deleteLearningPlan } from '../utils/api';
import { LearningPlan } from '../types/types';
import { PlusIcon, PencilIcon, TrashIcon, XMarkIcon, CheckIcon, ExclamationCircleIcon } from '@heroicons/react/24/outline';

function LearningPlans() {
  const [learningPlans, setLearningPlans] = useState<LearningPlan[]>([]);
  const [newLearningPlan, setNewLearningPlan] = useState({ title: '', topics: [''], timeline: '' });
  const [editLearningPlanId, setEditLearningPlanId] = useState<string | null>(null);
  const [editLearningPlanForm, setEditLearningPlanForm] = useState({
    title: '',
    topics: [''],
    timeline: '',
  });
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [formErrors, setFormErrors] = useState({
    title: '',
    topics: '',
    timeline: ''
  });
  const [editFormErrors, setEditFormErrors] = useState({
    title: '',
    topics: '',
    timeline: ''
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const userId = localStorage.getItem('userId') || 'user1';

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const plansRes = await getLearningPlans();
        setLearningPlans(plansRes.data);
      } catch (error) {
        console.error('Fetch learning plans error:', error);
      }
    };
    fetchPlans();
  }, []);

  // Validation function for create form
  const validateForm = () => {
    let valid = true;
    const errors = {
      title: '',
      topics: '',
      timeline: ''
    };

    // Check title
    if (!newLearningPlan.title.trim()) {
      errors.title = 'Title is required';
      valid = false;
    } else if (newLearningPlan.title.length > 100) {
      errors.title = 'Title must be less than 100 characters';
      valid = false;
    }

    // Check topics
    if (!newLearningPlan.topics[0]?.trim()) {
      errors.topics = 'At least one topic is required';
      valid = false;
    }

    // Check timeline
    if (!newLearningPlan.timeline.trim()) {
      errors.timeline = 'Timeline is required';
      valid = false;
    }

    setFormErrors(errors);
    return valid;
  };

  // Validation function for edit form
  const validateEditForm = () => {
    let valid = true;
    const errors = {
      title: '',
      topics: '',
      timeline: ''
    };

    // Check title
    if (!editLearningPlanForm.title.trim()) {
      errors.title = 'Title is required';
      valid = false;
    } else if (editLearningPlanForm.title.length > 100) {
      errors.title = 'Title must be less than 100 characters';
      valid = false;
    }

    // Check topics
    if (!editLearningPlanForm.topics[0]?.trim()) {
      errors.topics = 'At least one topic is required';
      valid = false;
    }

    // Check timeline
    if (!editLearningPlanForm.timeline.trim()) {
      errors.timeline = 'Timeline is required';
      valid = false;
    }

    setEditFormErrors(errors);
    return valid;
  };

  const handleCreateLearningPlan = async () => {
    if (!validateForm()) {
      return;
    }

    setIsSubmitting(true);
    try {
      await createLearningPlan({
        userId,
        title: newLearningPlan.title,
        topics: newLearningPlan.topics,
        timeline: newLearningPlan.timeline,
      });
      const plansRes = await getLearningPlans();
      setLearningPlans(plansRes.data);
      setNewLearningPlan({ title: '', topics: [''], timeline: '' });
      setShowCreateForm(false);
      setFormErrors({ title: '', topics: '', timeline: '' });
    } catch (error) {
      console.error('Create learning plan error:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleEditLearningPlan = (plan: LearningPlan) => {
    setEditLearningPlanId(plan.id);
    setEditLearningPlanForm({ title: plan.title, topics: plan.topics, timeline: plan.timeline });
    setEditFormErrors({ title: '', topics: '', timeline: '' });
  };

  const handleUpdateLearningPlan = async () => {
    if (!validateEditForm()) {
      return;
    }

    setIsSubmitting(true);
    try {
      await updateLearningPlan(editLearningPlanId!, {
        title: editLearningPlanForm.title,
        topics: editLearningPlanForm.topics,
        timeline: editLearningPlanForm.timeline,
      });
      const plansRes = await getLearningPlans();
      setLearningPlans(plansRes.data);
      setEditLearningPlanId(null);
      setEditLearningPlanForm({ title: '', topics: [''], timeline: '' });
      setEditFormErrors({ title: '', topics: '', timeline: '' });
    } catch (error) {
      console.error('Update learning plan error:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleDeleteLearningPlan = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this learning plan?')) {
      try {
        await deleteLearningPlan(id);
        setLearningPlans(learningPlans.filter((plan) => plan.id !== id));
      } catch (error) {
        console.error('Delete learning plan error:', error);
      }
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="bg-gradient-to-r from-blue-600 to-indigo-700 rounded-lg shadow-lg p-8 mb-10">
        <h1 className="text-3xl font-bold text-white mb-2">Learning Plans</h1>
        <p className="text-blue-100">Create and manage your personalized learning journey</p>
      </div>

      {/* Create Learning Plan Button */}
      {!showCreateForm && (
        <button
          onClick={() => setShowCreateForm(true)}
          className="mb-8 flex items-center gap-2 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-3 px-5 rounded-lg transition duration-300 shadow-md"
        >
          <PlusIcon className="h-5 w-5" />
          Create New Learning Plan
        </button>
      )}

      {/* Create Learning Plan Form */}
      {showCreateForm && (
        <div className="bg-white rounded-lg shadow-lg p-6 mb-10 border border-gray-100">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-semibold text-gray-800">Create Learning Plan</h2>
            <button 
              onClick={() => {
                setShowCreateForm(false);
                setFormErrors({ title: '', topics: '', timeline: '' });
                setNewLearningPlan({ title: '', topics: [''], timeline: '' });
              }}
              className="text-gray-500 hover:text-gray-700"
            >
              <XMarkIcon className="h-5 w-5" />
            </button>
          </div>
          
          <div className="space-y-4">
            <div>
              <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">Title *</label>
              <input
                id="title"
                type="text"
                placeholder="e.g., Master React in 3 Months"
                value={newLearningPlan.title}
                onChange={(e) => {
                  setNewLearningPlan({ ...newLearningPlan, title: e.target.value });
                  if (formErrors.title) setFormErrors({...formErrors, title: ''});
                }}
                className={`w-full px-4 py-2 border ${formErrors.title ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
              />
              {formErrors.title && (
                <p className="mt-1 text-sm text-red-600 flex items-center">
                  <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                  {formErrors.title}
                </p>
              )}
            </div>
            
            <div>
              <label htmlFor="topic" className="block text-sm font-medium text-gray-700 mb-1">Topics *</label>
              <input
                id="topic"
                type="text"
                placeholder="e.g., React, Redux, TypeScript"
                value={newLearningPlan.topics[0]}
                onChange={(e) => {
                  setNewLearningPlan({ ...newLearningPlan, topics: [e.target.value] });
                  if (formErrors.topics) setFormErrors({...formErrors, topics: ''});
                }}
                className={`w-full px-4 py-2 border ${formErrors.topics ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
              />
              {formErrors.topics && (
                <p className="mt-1 text-sm text-red-600 flex items-center">
                  <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                  {formErrors.topics}
                </p>
              )}
              <p className="mt-1 text-xs text-gray-500">Separate multiple topics with commas</p>
            </div>
            
            <div>
              <label htmlFor="timeline" className="block text-sm font-medium text-gray-700 mb-1">Timeline *</label>
              <input
                id="timeline"
                type="text"
                placeholder="e.g., 3 months, 1 hour daily"
                value={newLearningPlan.timeline}
                onChange={(e) => {
                  setNewLearningPlan({ ...newLearningPlan, timeline: e.target.value });
                  if (formErrors.timeline) setFormErrors({...formErrors, timeline: ''});
                }}
                className={`w-full px-4 py-2 border ${formErrors.timeline ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
              />
              {formErrors.timeline && (
                <p className="mt-1 text-sm text-red-600 flex items-center">
                  <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                  {formErrors.timeline}
                </p>
              )}
            </div>
            
            <div className="flex justify-end pt-2">
              <button 
                onClick={() => {
                  setShowCreateForm(false);
                  setFormErrors({ title: '', topics: '', timeline: '' });
                  setNewLearningPlan({ title: '', topics: [''], timeline: '' });
                }}
                className="mr-3 px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
                disabled={isSubmitting}
              >
                Cancel
              </button>
              <button 
                onClick={handleCreateLearningPlan}
                className={`px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${isSubmitting ? 'opacity-70 cursor-not-allowed' : ''}`}
                disabled={isSubmitting}
              >
                {isSubmitting ? 'Creating...' : 'Create Plan'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Learning Plans List */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {learningPlans.length === 0 ? (
          <div className="col-span-full flex flex-col items-center justify-center p-10 bg-gray-50 rounded-lg border border-dashed border-gray-300">
            <p className="text-gray-500 mb-4">You don't have any learning plans yet</p>
            <button
              onClick={() => setShowCreateForm(true)}
              className="text-indigo-600 font-medium hover:text-indigo-800"
            >
              Create your first plan
            </button>
          </div>
        ) : (
          learningPlans.map((plan) => (
            <div key={plan.id} className="bg-white rounded-lg overflow-hidden shadow-md border border-gray-100 hover:shadow-lg transition-shadow duration-300">
              {editLearningPlanId === plan.id ? (
                <div className="p-6">
                  <div className="space-y-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Title *</label>
                      <input
                        type="text"
                        value={editLearningPlanForm.title}
                        onChange={(e) => {
                          setEditLearningPlanForm({ ...editLearningPlanForm, title: e.target.value });
                          if (editFormErrors.title) setEditFormErrors({...editFormErrors, title: ''});
                        }}
                        className={`w-full px-3 py-2 border ${editFormErrors.title ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
                      />
                      {editFormErrors.title && (
                        <p className="mt-1 text-sm text-red-600 flex items-center">
                          <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                          {editFormErrors.title}
                        </p>
                      )}
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Topics *</label>
                      <input
                        type="text"
                        value={editLearningPlanForm.topics[0]}
                        onChange={(e) => {
                          setEditLearningPlanForm({ ...editLearningPlanForm, topics: [e.target.value] });
                          if (editFormErrors.topics) setEditFormErrors({...editFormErrors, topics: ''});
                        }}
                        className={`w-full px-3 py-2 border ${editFormErrors.topics ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
                      />
                      {editFormErrors.topics && (
                        <p className="mt-1 text-sm text-red-600 flex items-center">
                          <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                          {editFormErrors.topics}
                        </p>
                      )}
                      <p className="mt-1 text-xs text-gray-500">Separate multiple topics with commas</p>
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Timeline *</label>
                      <input
                        type="text"
                        value={editLearningPlanForm.timeline}
                        onChange={(e) => {
                          setEditLearningPlanForm({ ...editLearningPlanForm, timeline: e.target.value });
                          if (editFormErrors.timeline) setEditFormErrors({...editFormErrors, timeline: ''});
                        }}
                        className={`w-full px-3 py-2 border ${editFormErrors.timeline ? 'border-red-500' : 'border-gray-300'} rounded-md focus:ring-indigo-500 focus:border-indigo-500`}
                      />
                      {editFormErrors.timeline && (
                        <p className="mt-1 text-sm text-red-600 flex items-center">
                          <ExclamationCircleIcon className="h-4 w-4 mr-1" />
                          {editFormErrors.timeline}
                        </p>
                      )}
                    </div>
                    <div className="flex space-x-3 pt-2">
                      <button
                        onClick={handleUpdateLearningPlan}
                        className={`flex items-center justify-center gap-1 bg-green-600 hover:bg-green-700 text-white py-2 px-4 rounded-md ${isSubmitting ? 'opacity-70 cursor-not-allowed' : ''}`}
                        disabled={isSubmitting}
                      >
                        <CheckIcon className="h-4 w-4" /> {isSubmitting ? 'Saving...' : 'Save'}
                      </button>
                      <button
                        onClick={() => {
                          setEditLearningPlanId(null);
                          setEditFormErrors({ title: '', topics: '', timeline: '' });
                        }}
                        className="flex items-center justify-center gap-1 bg-gray-100 hover:bg-gray-200 text-gray-800 py-2 px-4 rounded-md"
                        disabled={isSubmitting}
                      >
                        <XMarkIcon className="h-4 w-4" /> Cancel
                      </button>
                    </div>
                  </div>
                </div>
              ) : (
                <>
                  <div className="bg-gradient-to-r from-blue-50 to-indigo-50 p-4">
                    <h3 className="font-bold text-xl text-gray-800">{plan.title}</h3>
                  </div>
                  <div className="p-6">
                    <div className="mb-4">
                      <h4 className="text-sm font-semibold text-gray-600 uppercase tracking-wider mb-2">Topics</h4>
                      <div className="flex flex-wrap gap-2">
                        {plan.topics.map((topic, index) => (
                          <span key={index} className="bg-indigo-100 text-indigo-800 text-sm px-3 py-1 rounded-full">
                            {topic}
                          </span>
                        ))}
                      </div>
                    </div>
                    <div>
                      <h4 className="text-sm font-semibold text-gray-600 uppercase tracking-wider mb-2">Timeline</h4>
                      <p className="text-gray-700">{plan.timeline}</p>
                    </div>
                    
                    {plan.userId === userId && (
                      <div className="flex justify-end mt-6 space-x-2">
                        <button
                          onClick={() => handleEditLearningPlan(plan)}
                          className="flex items-center gap-1 text-amber-600 hover:text-amber-800 font-medium text-sm"
                        >
                          <PencilIcon className="h-4 w-4" /> Edit
                        </button>
                        <button
                          onClick={() => handleDeleteLearningPlan(plan.id)}
                          className="flex items-center gap-1 text-red-600 hover:text-red-800 font-medium text-sm"
                        >
                          <TrashIcon className="h-4 w-4" /> Delete
                        </button>
                      </div>
                    )}
                  </div>
                </>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default LearningPlans;