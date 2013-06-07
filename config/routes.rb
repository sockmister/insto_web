Insto::Application.routes.draw do

  resources :location, :user

  # user has many requests
  resources :user do
    resources :request
    resources :submission
  end

  # location has many requests
  resources :location do
    resources :request
  end

  # custom action for /request/top
  resources :request do
    collection do
      get 'top'
    end
  end
end
