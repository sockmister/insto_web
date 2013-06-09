Insto::Application.routes.draw do

  resources :location, :user

  # user has many requests and submissions
  resources :user do
    resources :request
    resources :submission
  end

  # location has many requests and submissions
  resources :location do
    resources :request
    resources :submission
  end

  # custom action for /request/top
  resources :request do
    collection do
      get 'top'
    end
  end
end
