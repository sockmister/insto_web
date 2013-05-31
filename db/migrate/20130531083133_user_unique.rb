class UserUnique < ActiveRecord::Migration
  def up
  	add_index :users, :user, :unique => true
  end

  def down
  end
end
