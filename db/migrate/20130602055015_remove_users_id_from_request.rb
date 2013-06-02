class RemoveUsersIdFromRequest < ActiveRecord::Migration
  def up
  	remove_column :requests, :users_id
  end

  def down
  	add_column :requests, :users_id
  end
end
